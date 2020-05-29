package com.paytm.insider.service.common;

import java.util.ArrayList;

/**
 * Interface for Cache buffer mechanism in which sorting of data is done
 * through a Max Heap Data Structure
 */
public interface CacheBuffer {

    int QUEUE_LENGTH=10;

    public ArrayList getQueueBuffer();

    public void clearBuffer();
}

