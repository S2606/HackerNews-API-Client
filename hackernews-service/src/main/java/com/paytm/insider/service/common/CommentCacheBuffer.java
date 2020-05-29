package com.paytm.insider.service.common;

import com.paytm.insider.service.dto.HNCommentDto;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Comment data sorting mechanism
 */
public class CommentCacheBuffer implements CacheBuffer {
    private PriorityQueue<HNCommentDto> commentSorter;

    public CommentCacheBuffer() {
        commentSorter = new PriorityQueue<HNCommentDto>((a,b) -> {
            int count_a=a.getKids()==null?0:a.getKids().length;
            int count_b=b.getKids()==null?0:b.getKids().length;
            if(count_b>count_a){
                return 1;
            } else {
                return -1;
            }
        });
    }

    @Override
    public ArrayList getQueueBuffer(){
        return new ArrayList(commentSorter);
    }

    public synchronized void addToBuffer(HNCommentDto hnCommentDtoObj){
        if(commentSorter.size()>QUEUE_LENGTH){
            while(commentSorter.size()>QUEUE_LENGTH){
                commentSorter.poll();
            }
        }
        if(hnCommentDtoObj!=null)
            commentSorter.offer(hnCommentDtoObj);
    }

    @Override
    public void clearBuffer(){
        while(!commentSorter.isEmpty()){
            commentSorter.poll();
        }
    }

}
