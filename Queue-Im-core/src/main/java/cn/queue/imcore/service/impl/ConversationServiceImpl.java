package cn.queue.imcore.service.impl;

import cn.queue.common.domain.CommonResult;
import cn.queue.domain.dto.CreateConversationReq;
import cn.queue.domain.dto.SeqResDTO;
import cn.queue.domain.entity.ImConversationSetEntity;
import cn.queue.domain.pack.MessageReadContent;
import cn.queue.domain.valueObj.Constants;
import cn.queue.domain.valueObj.ConversationTypeEnum;
import cn.queue.imcore.dao.ImConversationSetMapper;
import cn.queue.imcore.repository.UserSequenceRepository;
import cn.queue.imcore.service.ConversationService;
import cn.queue.util.RedisSequence;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author BanTanger 半糖
 * @Date 2023/4/8 14:22
 */
@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {
    @Resource
    private final RedisSequence redisSequence;
    @Resource
    private final UserSequenceRepository userSequenceRepository;
    @Resource
    private final ImConversationSetMapper imConversationSetMapper;


    public CommonResult createConversation(@Valid CreateConversationReq req) {
        Integer conversationType = req.getConversationType();
        String conversationId = ConversationService.convertConversationId(
                conversationType, req.getFromId(), req.getToId());
        // 创建双方会话
        ImConversationSetEntity imConversationSetEntity = new ImConversationSetEntity();
        imConversationSetEntity.setAppId(req.getAppId());
        imConversationSetEntity.setFromId(req.getFromId());
        imConversationSetEntity.setToId(req.getToId());
        imConversationSetEntity.setConversationId(conversationId);
        imConversationSetEntity.setConversationType(conversationType);
        int insert = imConversationSetMapper.insert(imConversationSetEntity);
        if (insert != 1) {
            return CommonResult.fail("no");
        }
        return CommonResult.success();
    }
    @Override
     //获取当前会话未读消息数量
    public Long count(Long fromId, Long toId,Integer type){

        String conversationId = ConversationService.convertConversationId(
                type, fromId.toString(), toId.toString());
        long seq = redisSequence.doGetSeq(conversationId+
                Constants.SeqConstants.ConversationSeq);
        long readSeq = userSequenceRepository.getUserSeq(fromId.toString(),conversationId);
        return seq -readSeq;
    }
    @Override
    public void messageMarkRead(MessageReadContent messageReadContent) {
        // 抽离 toId, 有不同情况
        String toId = getToIdOrGroupId(messageReadContent);

        // conversationId: 1_fromId_toId
        String conversationId = ConversationService.convertConversationId(
                messageReadContent.getConversationType(), messageReadContent.getFromId(), toId);
        System.err.println(conversationId);
        /*
         记录当前会话的seq，表示当前最后一条消息
         */
        //更新当前会话seq
        long seq = redisSequence.doGetSeq(conversationId+
                Constants.SeqConstants.ConversationSeq);
        //将数据库里的数据更新
        imConversationSetMapper.readMark(buildReadMarkModel(messageReadContent, conversationId, seq));
        //将当前用户的会话的seq更新，表示当前用户的seq状态
        userSequenceRepository.writeUserSeq(messageReadContent.getFromId(), conversationId, seq);
    }


//    @Override
//    public ResponseVO deleteConversation(@Valid DeleteConversationReq req) {
//        if (appConfig.getDeleteConversationSyncMode() == 1) {
//            DeleteConversationPack pack = new DeleteConversationPack();
//            pack.setConversationId(req.getConversationId());
//            messageProducer.sendToUserExceptClient(req.getFromId(),
//                    ConversationEventCommand.CONVERSATION_DELETE,
//                    pack, new ClientInfo(req.getAppId(), req.getClientType(),
//                            req.getImei()));
//        }
//        return ResponseVO.successResponse();
//    }
//
//    @Override
//    public ResponseVO updateConversation(@Valid UpdateConversationReq req) {
//        if (req.getIsTop() == null && req.getIsMute() == null) {
//            return ResponseVO.errorResponse(ConversationErrorCode.CONVERSATION_UPDATE_PARAM_ERROR);
//        }
//        QueryWrapper<ImConversationSetEntity> query = new QueryWrapper<>();
//        query.eq("conversation_id", req.getConversationId());
//        query.eq("app_id", req.getAppId());
//        ImConversationSetEntity imConversationSetEntity = imConversationSetMapper.selectOne(query);
//        if (imConversationSetEntity != null) {
//            long seq = redisSequence.doGetSeq(req.getAppId() + Constants.SeqConstants.ConversationSeq);
//            if (req.getIsMute() != null) {
//                // 更新禁言状态
//                imConversationSetEntity.setIsMute(req.getIsMute());
//            }
//            if (req.getIsTop() != null) {
//                // 更新置顶状态
//                imConversationSetEntity.setIsTop(req.getIsTop());
//            }
//            imConversationSetEntity.setSequence(seq);
//            imConversationSetMapper.update(imConversationSetEntity, query);
//
//            userSequenceRepository.writeUserSeq(req.getAppId(),
//            req.getFromId(), Constants.SeqConstants.ConversationSeq, seq);
//
//            UpdateConversationPack pack = new UpdateConversationPack();
//            pack.setConversationId(req.getConversationId());
//            pack.setIsMute(imConversationSetEntity.getIsMute());
//            pack.setIsTop(imConversationSetEntity.getIsTop());
//            pack.setSequence(seq);
//            pack.setConversationType(imConversationSetEntity.getConversationType());
//            messageProducer.sendToUserExceptClient(req.getFromId(),
//            ConversationEventCommand.CONVERSATION_UPDATE,
//            pack, new ClientInfo(req.getAppId(), req.getClientType(),
//            req.getImei()));
//        }
//        return ResponseVO.successResponse();
//    }
//    @Override
//    public ResponseVO syncConversationSet(SyncReq req) {
//        if (req.getMaxLimit() > appConfig.getConversationMaxCount()) {
//            req.setMaxLimit(appConfig.getConversationMaxCount());
//        }
//
//        SyncResp<ImConversationSetEntity> resp = new SyncResp<>();
//
//        QueryWrapper<ImConversationSetEntity> query = new QueryWrapper<>();
//        query.eq("from_id", req.getOperater());
//        query.gt("sequence", req.getLastSequence());
//        query.eq("app_id", req.getAppId());
//        query.last("limit " + req.getMaxLimit());
//        query.orderByAsc("sequence");
//        List<ImConversationSetEntity> list = imConversationSetMapper.selectList(query);
//
//        if (!CollectionUtils.isEmpty(list)) {
//            ImConversationSetEntity maxSeqEntity = list.get(list.size() - 1);
//            resp.setDataList(list);
//            // 设置最大 Seq
//            Long conversationMaxSeq = imConversationSetMapper
//                    .getConversationMaxSeq(req.getAppId());
//            resp.setMaxSequence(conversationMaxSeq);
//            // 设置是否拉取完毕
//            resp.setCompleted(maxSeqEntity.getSequence() >= conversationMaxSeq);
//            return ResponseVO.successResponse(resp);
//        }
//        resp.setCompleted(true);
//        return ResponseVO.successResponse(resp);
//    }
//
    private static ImConversationSetEntity buildReadMarkModel(MessageReadContent messageReadContent, String conversationId, long seq) {
        ImConversationSetEntity imConversationSetEntity = new ImConversationSetEntity();
        imConversationSetEntity.setConversationId(conversationId);
        imConversationSetEntity.setSequence(seq);
        imConversationSetEntity.setReadSequence(messageReadContent.getMessageSequence());
        return imConversationSetEntity;

    }
//
    private static String getToIdOrGroupId(MessageReadContent messageReadContent) {
        // 会话类型为单聊，toId 赋值为目标用户
        String toId = messageReadContent.getToId();
        if (ConversationTypeEnum.GROUP.getCode().equals(messageReadContent.getConversationType())) {
            // 会话类型为群聊，toId 赋值为 groupId
            toId = messageReadContent.getGroupId();
        }
        return toId;
    }
}
