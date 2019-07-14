package cn.wolfcode.service.impl;

import cn.wolfcode.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleTask {

    @Autowired
    private IBidRequestService bidRequestService;

    /**
     * 检查实时发标的思路
     *  第一种思路  :  每隔1秒中就去数据库检查对应的对应的未发布的标是否已经发送   但是效率比较的低
     *  第二种思路  :
     *      1. 将数据存储到内存中  每次只需要在内存中查找即可
     *              用什么作为内存  ConcurrentLinkedQueue<BidRequest>  作为内存 因为是线程安全的
     *      2. 如何把数据存储到缓存中
     *              1. 如果没审核一个的话需要将该标加入到内存中
     *              2. 当系统在重启的时候  需要查询数据库 存储到对应的额内存中
     *              3. 需要在定义一个定时器  在查询数据库中的的待发队列(这个定时器的间隔时间需要长一点)
     *              4. 上一是以防万一有些没有添加 所以在添加得时候一定要进行去重操作
     *      3. 从缓存中取出对应的数据  利用定时器 每秒去检查对应的缓存队列判断是否有需要发标的
     *              如果有偶发标的修改数据库的标的状态  并且从缓存中删除该待发标
     */

    /*
     *  这个是从缓存中取出对应的标
     */
    @Scheduled(cron = "*/1 * * * * *")
    public void cronTask() {
        bidRequestService.releaseBidRequestByCache();
    }

    /**
     * 利用定时器查询数据库中待发布的标
     */
    @Scheduled(cron = "*/60 * * * * *")
    public void addPendingReleaseBidRequest2Cache() {
        bidRequestService.addPendingReleaseBidRequest2Cache();
    }
}