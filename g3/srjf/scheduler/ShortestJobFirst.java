package g3.srjf.scheduler;

import java.util.Arrays;
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

  static void test1() {
    var processes = Arrays.asList(
        new PCB("P1", 12, 1),
        new PCB("P2", 4, 2),
        new PCB("P3", 6, 3),
        new PCB("P4", 5, 8));
    var srjf = new ShortestJobFirst(processes);
    var scheduleSnapshot = srjf.shortestRemainingJobFirstScheduler(false);
    print(scheduleSnapshot);
    print(processes);
    print("Turnaround time", srjf.getTurnAroundTime());
    print("Response time", srjf.getResponseTime());
    print("Completion Time", srjf.getCompletionTime());
    print("Waiting time", srjf.getWaitingTime());

    print("Average turnaround time", srjf.getAverageTurnAroundTime());
    print("Average waiting time", srjf.getAverageWaitingTime());
    print("Average response time", srjf.getAverageResponseTime());
    print("Throughput", srjf.getThroughput());
  }

  static void test2() {
    var processes = Arrays.asList(
        new PCB("P1", 6),
        new PCB("P2", 8),
        new PCB("P3", 7),
        new PCB("P4", 3));

    var srjf = new ShortestJobFirst(processes);
    var scheduleSnapshot = srjf.shortestRemainingJobFirstScheduler(false);
    print(scheduleSnapshot);
    print("Turnaround time", srjf.getTurnAroundTime());
    print("Response time", srjf.getResponseTime());

    print("Average turnaround time", srjf.getAverageTurnAroundTime());
    print("Average waiting time", srjf.getAverageWaitingTime());
    print("Average response time", srjf.getAverageResponseTime());
    print("Throughput", srjf.getThroughput());
  }

  static void test3() {
    var processes = Arrays.asList(
        new PCB("P1", 10, 0),
        new PCB("P2", 6, 3),
        new PCB("P3", 1, 7),
        new PCB("P4", 3, 8));
    var srjf = new ShortestJobFirst(processes);
    var scheduleSnapshot = srjf.shortestRemainingJobFirstScheduler(true);
    print(scheduleSnapshot);
    print("Turnaround time", srjf.getTurnAroundTime());
    print("Response time", srjf.getResponseTime());

    print("Average turnaround time", srjf.getAverageTurnAroundTime());
    print("Average waiting time", srjf.getAverageWaitingTime());
    print("Average response time", srjf.getAverageResponseTime());
    print("Throughput", srjf.getThroughput());
  }

  static void test4() {
    var processes = Arrays.asList(
        new PCB("P1", 27),
        new PCB("P2", 13),
        new PCB("P3", 7),
        new PCB("P4", 17),
        new PCB("P5", 13),
        new PCB("P6", 9));

    var srjf = new ShortestJobFirst(processes);
    var scheduleSnapshot = srjf.shortestRemainingJobFirstScheduler(false);
    print(scheduleSnapshot);
    print("Turnaround time", srjf.getTurnAroundTime());
    print("Response time", srjf.getResponseTime());

    print("Average turnaround time", srjf.getAverageTurnAroundTime());
    print("Average waiting time", srjf.getAverageWaitingTime());
    print("Average response time", srjf.getAverageResponseTime());
    print("Throughput", srjf.getThroughput());
  }
  
  static void test5() {
    var processes = Arrays.asList(
        new PCB("P1", 27, 0),
        new PCB("P2", 18, 3),
        new PCB("P3", 20, 6),
        new PCB("P4", 13, 9),
        new PCB("P5", 9, 12),
        new PCB("P6", 16, 15));

    var srjf = new ShortestJobFirst(processes);
    var scheduleSnapshot = srjf.shortestRemainingJobFirstScheduler(false);
    print(scheduleSnapshot);
    print("Turnaround time", srjf.getTurnAroundTime());
    print("Response time", srjf.getResponseTime());

    print("Average turnaround time", srjf.getAverageTurnAroundTime());
    print("Average waiting time", srjf.getAverageWaitingTime());
    print("Average response time", srjf.getAverageResponseTime());
    print("Throughput", srjf.getThroughput());
  }
  
  static void test6() {
    var processes = Arrays.asList(
        new PCB("P1", 27, 0),
        new PCB("P2", 18, 5),
        new PCB("P3", 20, 8),
        new PCB("P4", 13, 12),
        new PCB("P5", 9, 14));

    var srjf = new ShortestJobFirst(processes);
    var scheduleSnapshot = srjf.shortestRemainingJobFirstScheduler(true);
    print(scheduleSnapshot);
    print("Turnaround time", srjf.getTurnAroundTime());
    print("Response time", srjf.getResponseTime());

    print("Average turnaround time", srjf.getAverageTurnAroundTime());
    print("Average waiting time", srjf.getAverageWaitingTime());
    print("Average response time", srjf.getAverageResponseTime());
    print("Throughput", srjf.getThroughput());
  }
  
  static void test7() {
    var processes = Arrays.asList(
        new PCB("P1", 29,0),
        new PCB("P2", 20,7),
        new PCB("P3", 15,10),
        new PCB("P4", 10,14),
        new PCB("P5", 9,15),
        new PCB("P6", 6,16));

    var srjf = new ShortestJobFirst(processes);
    var scheduleSnapshot = srjf.shortestRemainingJobFirstScheduler(true);
    print(scheduleSnapshot);
    print("Turnaround time", srjf.getTurnAroundTime());
    print("Response time", srjf.getResponseTime());

    print("Average turnaround time", srjf.getAverageTurnAroundTime());
    print("Average waiting time", srjf.getAverageWaitingTime());
    print("Average response time", srjf.getAverageResponseTime());
    print("Throughput", srjf.getThroughput());
  }

  public static void main(String[] args) {
    test1();
  }
}