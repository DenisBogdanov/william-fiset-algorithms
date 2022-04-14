package com.williamfiset.algorithms.datastructures.queue;

/**
 * @param <T> the type of element held int the queue
 * @author liujingkun, liujkon@gmail.com
 */
public interface Queue<T> {
  public void offer(T elem);

  public T poll();

  public T peek();

  public int size();

  public boolean isEmpty();
}
