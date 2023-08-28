package oy.tol.tra;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A class showing your daily schedule using a timer.
 */
public class DailyTasks {

   private QueueInterface<String> dailyTaskQueue = null;
   private Timer timer = null;
   private static final int TASK_DELAY_IN_SECONDS = 1 * 1000;

   private DailyTasks() {
   }

   /** 
    * Execute from the command line:  <code>java -jar target/04-queue-1.0-SNAPSHOT-jar-with-dependencies.jar</code>
    * @param args Not used.
    */
   public static void main(String[] args) {
      DailyTasks tasks = new DailyTasks();
      tasks.run();
   }

   private void run() {
      // TODO: Uncomment the try / catch when finishing the implementation below!
      // try {
         // TODO: Fill in the missing empty lines with correct functional code!
         
         // 1. create a queue (to the member variable!) for daily tasks, which are strings.

         // 2. read the tasks for today by calling readTasks() -- implementing missing parts of it!

         // 3. create Java Timer object (to member variable!!) to schedule your daily tasks.

         // 4. schedule the timer at fixed rate (!!!!) with a new TimerTask,
         //  using the delay constant values in the class member variable.

         // 4.1 in the timer task run:

               // 4.1.1 check if there are tasks in the queue:
               // 4.1.2 if yes, print the task from the queue, dequeueing it.
               // 4.1.3 if not, cancel the timer.

      // } catch (IOException e) {
      //    System.out.println("Something went wrong :( " + e.getLocalizedMessage());
      // }
      try{
         dailyTaskQueue = new QueueImplementation<>();
         readTasks();
         timer = new Timer("Timer");
         TimerTask tTask = new TimerTask() {
            public void run(){
               if(dailyTaskQueue.isEmpty() == false){
                  System.out.println("Dequeued task: " + dailyTaskQueue.dequeue());
               }else{
                  timer.cancel();
               }
            } 
         };
         timer.scheduleAtFixedRate(tTask, TASK_DELAY_IN_SECONDS, TASK_DELAY_IN_SECONDS);
      }catch(IOException e){
         System.out.println("Something went wrong :( " + e.getLocalizedMessage());
      }
   }

   private void readTasks() throws IOException {
      String tasks;
      tasks = new String(getClass().getClassLoader().getResourceAsStream("DailyTasks.txt").readAllBytes());
      String[] allTasks = tasks.split("\\r?\\n");
      for (String task : allTasks) {
         // TODO: Enqueue the task to your Queue implementation:
         dailyTaskQueue.enqueue(task);
      }
      // TODO: print out to the console the number of tasks in the queue:
      System.out.println("Number of tasks in queue: " + dailyTaskQueue.size());
   }
}
