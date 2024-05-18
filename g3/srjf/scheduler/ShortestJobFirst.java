package g3.srjf.scheduler;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ShortestJobFirst extends Scheduler {
  public ShortestJobFirst(List<PCB> processes) {
    super(processes);
  }

  /**
   * 
   * @param isPreemptive : tell the scheduler to wether the coming process can pre-empite the currently
   *                       executing process or not, default value is true
   * @return snapshot of the process exection as a linked list
   */
  public LinkedList<ExecutionSnapshot> shortestRemainingJobFirstScheduler(boolean isPreemptive) {
    /**
     * The process queue will be in the order of their arrival time and if two
     * process arrives at the same time, their burst time will be used as a comparator
     */
    Comparator<PCB> processQueueComparator = (PCB p1, PCB p2) -> {
      return p1.getArrivalTime() == p2.getArrivalTime() ? p1.getBurstTime() - p2.getBurstTime()
          : p1.getArrivalTime() - p2.getArrivalTime();
    };

    /**
     * The ready queue will be order based on the burst time, since the job with shortest
     * burst time need to be at the front of the process priotity queue
     */
    Comparator<PCB> readyQueueComparator = (PCB p1, PCB p2) -> p1.getBurstTime() - p2.getBurstTime();
    return schedule(processQueueComparator, readyQueueComparator, isPreemptive);
  }
  
  public LinkedList<ExecutionSnapshot> shortestRemainingJobFirstScheduler() {
    return shortestRemainingJobFirstScheduler(true);
  }

}