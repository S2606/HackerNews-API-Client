package com.paytm.insider.service.common;

import com.paytm.insider.service.dto.HNStoryDto;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Story data sorting mechanism
 */
public class StoryCacheBuffer implements CacheBuffer {
    private PriorityQueue<HNStoryDto> storysorter;

    public StoryCacheBuffer(){
        storysorter = new PriorityQueue<HNStoryDto>((a,b) -> {
            int count_a=a.getScore()==null?0:a.getScore();
            int count_b=b.getScore()==null?0:a.getScore();
            if(count_b>count_a){
                return 1;
            } else {
                return -1;
            }
        });
    }

    @Override
    public ArrayList getQueueBuffer() {
        return new ArrayList(storysorter);
    }

    public synchronized void addToBuffer(HNStoryDto hnStoryDtoObj){
        if(storysorter.size()>QUEUE_LENGTH){
            storysorter.poll();
        }
        if(hnStoryDtoObj!=null)
            storysorter.offer(hnStoryDtoObj);
    }

    @Override
    public void clearBuffer(){
        while(!storysorter.isEmpty()){
            storysorter.poll();
        }
    }
}
