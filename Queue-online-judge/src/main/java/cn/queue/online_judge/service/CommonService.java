package cn.queue.online_judge.service;

public interface CommonService {

    /**
     * 检测比赛是否在进行
     * @param comId
     * @param userId
     * @return
     */
    boolean checkCompetition(Long comId, Long userId);


    /**
     *检测是否可以解锁
      * @param comId
     * @return
     */
    boolean checkUnlock(Long comId);


    /**
     * 判分
     * @param comId
     * @param userId
     * @return
     */
    int judgeScore(Long comId, Long userId);
}
