package g3.srjf.scheduler;

public class ExecutionSnapshot {
  private String processId;
  private int tInitial;
  private int tFinal;

  /**
   * This class is used to represent the process the instant it is under execution
   * by the CPU
   * 
   * @param processId reprents the process under execution
   * @param tInitial the instant the CPU began exectuing the process
   * @param tFinal the instant the CPU finished/suspended executing the process
   */
  public ExecutionSnapshot(String processId, int tInitial, int tFinal) {
    this.processId = processId;
    this.tInitial = tInitial;
    this.tFinal = tFinal;
  }

  public String getProcessId() {
    return processId;
  }

  public void setProcessId(String processId) {
    this.processId = processId;
  }

  public int gettInitial() {
    return tInitial;
  }

  public void settInitial(int tInitial) {
    this.tInitial = tInitial;
  }

  public int gettFinal() {
    return tFinal;
  }

  public void settFinal(int tFinal) {
    this.tFinal = tFinal;
  }

}
