package g3.srjf.scheduler;

import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Scheduler {
  /*
   * List of processes to be executed. Each process is represented by a PCB
   * (Process Control Block) object.
   */
  private List<PCB> processes;

  /*
   * Map to store the turnaround time for each process. The key is the process id
   * (String), and the value is the turnaround time (Integer). Turnaround time is the total
   * time taken from the submission of a process to its completion.
   */
  private Map<String, Integer> turnAroundTime;

  /*
   * Map to store the waiting time for each process. The key is the process id
   * (String), and the value is the waiting time (Integer). Waiting time is the total time
   * spent by the process in the ready state waiting for the CPU. It is calculated as
   * turnaround time minus burst time (the actual time the process spends
   * executing on the CPU).
   */
  private Map<String, Integer> waitingTime;

  /*
   * Map to store the completion time for each process. The key is the process id
   * (String), and the value is the completion time (Integer). Completion time is the time
   * at which the process finishes its execution.
   */
  private Map<String, Integer> completionTime;

  /*
   * Map to store the response time for each process. The key is the process id
   * (String), and the value is the response time (Integer). Response time is the time from
   * submission (arrival time) of a request until the first response is produced (i.e., the first
   * time the process gets the CPU).
   */
  private Map<String, Integer> responseTime;

  /*
   * Linked list to store the schedule table, which contains snapshots of the
   * execution at different times.
   * Each snapshot is represented by an ExecutionSnapshot object, which captures
   * the state of the system at a specific time.
   */
  private LinkedList<ExecutionSnapshot> scheduleTable;

  /*
   * Average turnaround time for all processes. This is calculated as the sum of
   * the turnaround times of all processes divided by the total number of processes.
   */
  private double averageTurnAroundTime;

  /*
   * Average waiting time for all processes. This is calculated as the sum of the
   * waiting times of all processes divided by the total number of processes.
   */
  private double averageWaitingTime;

  /*
   * Average response time for all processes. This is calculated as the sum of the
   * response times of all processes divided by the total number of processes.
   */
  private double averageResponseTime;

  /*
   * Throughput of the system. Throughput is the number of processes completed per
   * unit of time. This metric gives an indication of the overall efficiency of the process
   * scheduling.
   */
  private double throughput;

  /**
   * @param processes list of processes to be executed
   */

  public Scheduler(List<PCB> processes) {
    this.processes = processes;
    this.turnAroundTime = new HashMap<>();
    this.waitingTime = new HashMap<>();
    this.completionTime = new HashMap<>();
    this.responseTime = new HashMap<>();
    this.scheduleTable = new LinkedList<>();
    this.averageTurnAroundTime = 0D;
    this.averageWaitingTime = 0D;
    this.averageResponseTime = 0D;
    this.throughput = 0D;
  }

  public List<PCB> getProcesses() {
    return processes;
  }
  public void setProcesses(List<PCB> processes) {
    this.processes = processes;
  }
  public Map<String, Integer> getTurnAroundTime() {
    return turnAroundTime;
  }
  public void setTurnAroundTime(Map<String, Integer> turnAroundTime) {
    this.turnAroundTime = turnAroundTime;
  }
  public Map<String, Integer> getWaitingTime() {
    return waitingTime;
  }
  public void setWaitingTime(Map<String, Integer> waitingTime) {
    this.waitingTime = waitingTime;
  }
  public Map<String, Integer> getCompletionTime() {
    return completionTime;
  }
  public void setCompletionTime(Map<String, Integer> completionTime) {
    this.waitingTime = completionTime;
  }
  public LinkedList<ExecutionSnapshot> getScheduleTable() {
    return this.scheduleTable;
  }
  public void setScheduleTable(LinkedList<ExecutionSnapshot> scheduleTable) {
    this.scheduleTable = scheduleTable;
  }
  public double getAverageTurnAroundTime() {
    return averageTurnAroundTime;
  }
  public void setAverageTurnAroundTime(double averageTurnAroundTime) {
    this.averageTurnAroundTime = averageTurnAroundTime;
  }
  public double getAverageWaitingTime() {
    return averageWaitingTime;
  }
  public void setAverageWaitingTime(double averageWaitingTime) {
    this.averageWaitingTime = averageWaitingTime;
  }
  public Map<String, Integer> getResponseTime() {
    return responseTime;
  }
  public void setResponseTime(Map<String, Integer> responseTime) {
    this.responseTime = responseTime;
  }
  public double getAverageResponseTime() {
    return averageResponseTime;
  }
  public void setAverageResponseTime(double averageResponseTime) {
    this.averageResponseTime = averageResponseTime;
  }
  public double getThroughput() {
    return throughput;
  }
  public void setThroughput(double throughput) {
    this.throughput = throughput;
  }
  public PCB getProcess(String pID){
    return processes.stream().filter(p -> p.getPID().equals(pID)).findFirst().get();
  }
  
  public void saveSnapshot(String pId, int tInit, int tFinal) {
    if (!scheduleTable.isEmpty() && scheduleTable.getLast().getProcessId().equals(pId))
      scheduleTable.getLast().settFinal(tFinal);
    else
      scheduleTable.addLast(new ExecutionSnapshot(pId, tInit, tFinal));
  }

  public void computeResponseTime() {
    var processSnapshot = this.getScheduleTable();
    var it = processSnapshot.iterator();
    while (it.hasNext()) {
      var curr = it.next();
      if(curr.getProcessId().contains("--")) continue;
      if (!responseTime.containsKey(curr.getProcessId())) {
        responseTime.put(curr.getProcessId(), curr.gettInitial());
      }
    }

    double sum = 0.0;
    var mapIt = responseTime.entrySet().iterator();
    while (mapIt.hasNext()) {
      sum += mapIt.next().getValue();
    }

    this.averageResponseTime = sum / responseTime.size();
  }
  public void computeCompletionTime() {
    var processSnapshot = this.getScheduleTable();
    var it = processSnapshot.iterator();
    while (it.hasNext()) {
      var curr = it.next();
      if(curr.getProcessId().contains("--")) continue;
      completionTime.put(curr.getProcessId(), curr.gettFinal());
    }
  }
  public void computeTurnAroundTime(){
    var processSnapshot = this.getScheduleTable();
    var it = processSnapshot.iterator();
    while (it.hasNext()) {
      var curr = it.next();
      if(curr.getProcessId().contains("--")) continue;  
      turnAroundTime.put(curr.getProcessId(), curr.gettFinal() - getProcess(curr.getProcessId()).getArrivalTime());
    }

    double sum = 0.0;
    var mapIt = turnAroundTime.entrySet().iterator();
    while (mapIt.hasNext()) {
      sum += mapIt.next().getValue();
    }

    this.averageTurnAroundTime = sum / turnAroundTime.size();
  }
  public void computeWaitingTime(){
    var processSnapshot = this.getScheduleTable();
    var it = processSnapshot.iterator();
    while (it.hasNext()) {
      var curr = it.next();
      if(curr.getProcessId().contains("--")) continue;
      waitingTime.put(curr.getProcessId(),
          turnAroundTime.get(curr.getProcessId()) - getProcess(curr.getProcessId()).getBurstTime());
    }

    double sum = 0.0;
    var mapIt = waitingTime.entrySet().iterator();
    while (mapIt.hasNext()) {
      sum += mapIt.next().getValue();
    }

    this.averageWaitingTime = sum / waitingTime.size();
  }
  public void computeThroughput(){
    this.throughput = (double) processes.size() / scheduleTable.getLast().gettFinal();
  }
  
  public static void print(LinkedList<ExecutionSnapshot> scheduleTable) {
    System.out.println(" -------- Process Execution Schedule -------- ");
    // for (var row : scheduleTable)
    //   System.out.println("  [" + row.gettInitial() + " <- " + row.getProcessId() + " -> " + row.gettFinal() + "]");
      var it = scheduleTable.iterator();
      String row = "[";
      
      while (it.hasNext()) {
        var curr = it.next();
        row += curr.gettInitial() + " <- " + curr.getProcessId() + " -> ";
      }
      row += scheduleTable.getLast().gettFinal() + "]";
      System.out.println(row);
      System.out.println(" -------- ------- --------- -------- -------- ");
  }
  public static void print(List<PCB> processes) {
    System.out.println(" ----------------- PROCESSES ---------------- ");
    System.out.println("PID   BurstT(ms)     ArrivalT(ms)     Priority");
    for (var procces : processes) {
      System.out.println(
          procces.getPID() + "    " + procces.getBurstTime() + "              " + procces.getArrivalTime()
              + "                "
              + procces.getPriority());
    }
    System.out.println(" -------------------------------------------- ");
  }
  public static void print(String header, Map<String, Integer> mapData) {
    System.out.println(" ------ " + header + " ------ ");
    for (var entry : mapData.entrySet())
      System.out.println("   " + entry.getKey() + " : " + entry.getValue() + "ms");
    System.out.println(" ------ ---------- ----- ------ ");
  }
  public static void print(String header, double value) {
    if(header.contains("Throughput") || header.contains("throughput"))
      System.out.println(" - " + header + ": " + value + "proc/ms");
    else
      System.out.println(" - " + header + ": " + value + "ms");
  }

  /**
   * @param processQueueComparator Comparator to sort the proccess queue
   * @param readyQueueComparator   The comparator used to order ready queue inside the priority queue
   * @param isPreemptive  tells the scheduler whether to use preemptive or non-premeemptive scheduling
   * @return schedule(execution) snapshot as a LinkedList of {@code ExecutionSnapshot }
   */
  public LinkedList<ExecutionSnapshot> schedule(Comparator<PCB> processQueueComparator, Comparator<PCB> readyQueueComparator, boolean isPreemptive) {
    // To not mutate the original process queue: make deep copy of the queue to work with
    var processesCopy = new LinkedList<PCB>();
    for (PCB process : processes) processesCopy.add(process.clone());
    
    // Check if the process queue is empty
    if (processesCopy.size() == 0) {
      scheduleTable.addLast(new ExecutionSnapshot("Empty Process Block", 0, 0));
      return scheduleTable;
    }

    // Order the process queue based on the queue comparator
    processesCopy.sort(processQueueComparator);

    /**
     * Make a ready queue (process priority queue), remove the first process in the process queue and add 
     * it to the ppq. ppq is a priority queue in which it is always guaranteed that element with highest 
     * priority is at the top/front (depends on the readyQueueComparator) provided 
     * 
     * timer: used as clock to keep track of the cpu time
     **/
    var ppq = new PriorityQueue<PCB>(readyQueueComparator);
    ppq.add(processesCopy.removeFirst());
    var timer = 0;

    // As long as ready-queue is not empty: the processor keeps executing
    while (!ppq.isEmpty()) {
      /**
       * If the process at the front of the top of the ready queue arrived late meaning arrived after the timer
       * has started, there is cpu cycle wastage we need to keep track of.
       * Example if the first process arrives 1second after the timer started
       */
      if (ppq.peek().getArrivalTime() > timer) {
        saveSnapshot("--", timer, ppq.peek().getArrivalTime());
        timer = ppq.peek().getArrivalTime();
        continue;
      }

      /** Take the front process inside the ready queue for execution */
      var currentProcess = ppq.poll();

      /**
       * In case of preemptive scheduling: Until the next processe's arrival time, the currently executing process
       * goes on execution unless it is done executing. We do this because we need to make sure if the next process
       * has lower burst time/higher priority it will pre-empete the currently executing process.
       * 
       * let's find the next arrival time : 
       *    If the processCopy is empty we have no next process
       *    Else we will take the process at the front inside the process queue and get it arrival time
       **/
      var nextProcessArrivalTime = processesCopy.isEmpty() ? null : processesCopy.getFirst().getArrivalTime();

      /**
       * Then:
       *  - If the scheduling is not preemptive: the next coming process will not pre-empete the currently executing process
       *  - Or If there is no more process to be executed
       *  - Or If the currently executing process gets done executing before the next process arrives
       * Execution can just continue and
       *    # Save the snapshot of the exectuion
       *    # Advance the timer by burst time of the executed process (time it took to execute the current process)
       *    # Save - completion time and update the timer
       */
      if (!isPreemptive || processesCopy.isEmpty() || timer + currentProcess.getBurstTime() <= nextProcessArrivalTime) {
        saveSnapshot(currentProcess.getPID(), timer, timer + currentProcess.getBurstTime());
        timer += currentProcess.getBurstTime();
      }
      /** Else
       * The scheduling is preemptive and there is next process to be executed and the current process will not be done executed,
       * thus we let the current process execute partly  or till the next process arrives and then push it back to ppq or the 
       * ready queue with the remaining burst time
       * 
       * - The remaining burst time for the process is calculated as 
       *      # timer + current process burst time - next process arrival time
       * - And advance the timer to the next processe's arrival time
       */
      else {
        saveSnapshot(currentProcess.getPID(), timer, nextProcessArrivalTime);
        currentProcess.setBurstTime(currentProcess.getBurstTime() - (nextProcessArrivalTime - timer));
        timer = nextProcessArrivalTime;
        ppq.add(currentProcess);
      }

      /**
       * If there are other processes arrived till now, we move them from the process queue to the ready the ready queue  
       **/
      while (!processesCopy.isEmpty() && processesCopy.getFirst().getArrivalTime() <= timer)
        ppq.add(processesCopy.removeFirst());

      /**
       * Check if processCopy is not empty and readyQueue is empty: this means that the process at the front of the process queue
       * has not arrived yet, thus we have to forcefully push it inside the ready queue: other wise the execution will cease before finishing
       */
      if (!processesCopy.isEmpty() && ppq.isEmpty())
        ppq.add(processesCopy.removeFirst());
    }
    /**
     * Finally calculate completion, turnaround, waiting, and response times
     * And the throughput of the cpu
     */
    
    computeCompletionTime();
    computeTurnAroundTime();
    computeWaitingTime();
    computeResponseTime();
    computeThroughput();
    /* and return the execution snapshot */
    return this.scheduleTable;
  }
}