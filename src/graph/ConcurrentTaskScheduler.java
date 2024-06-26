package graph;

import java.util.*;

class AdjTask {
    private final List<Task> nextTasks;
    private final List<Task> dependencyTasks;

    public AdjTask() {
        this.nextTasks = new ArrayList<>();
        this.dependencyTasks = new ArrayList<>();
    }

    public List<Task> getNextTasks() {
        return nextTasks;
    }

    public List<Task> getDependencyTasks() {
        return dependencyTasks;
    }
}

class DAG {
    private final Map<String, Task> tasks;
    private final Map<Task, AdjTask> adjList;

    DAG() {
        this.tasks = new HashMap<>();
        this.adjList = new HashMap<>();
    }

    void addTask(String name) {
        Task task = new Task(name);
        tasks.put(name, task);
        adjList.put(task, new AdjTask());
    }

    void addEdge(String from, String to) {
        Task fromTask = tasks.get(from);
        Task toTask = tasks.get(to);
        if (fromTask != null && toTask != null) {
            adjList.get(toTask).getDependencyTasks().add(fromTask);
            adjList.get(fromTask).getNextTasks().add(toTask);
        }
    }

    void topologicalSortAndExecute() {
        TreeMap<Integer, List<Task>> levelMap = buildLevelMap(new ArrayList<>(tasks.values()));
        levelMap.forEach((level, levelTasks) -> {
            List<Thread> threads = new ArrayList<>();
            for (Task task : levelTasks) {
                System.out.println("准备执行任务: " + task.getName());
                Thread thread = new Thread(task);
                thread.start();
                threads.add(thread);
            }
            for (Thread thread : threads) {
                try {
                    thread.join(); // 等待任务执行完毕
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private TreeMap<Integer, List<Task>> buildLevelMap(List<Task> sortedTasks) {
        TreeMap<Integer, List<Task>> levelMap = new TreeMap<>();
        for (Task task : sortedTasks) {
            int level = getTaskLevel(task);
            levelMap.computeIfAbsent(level, key -> new ArrayList<>()).add(task);
        }
        return levelMap;
    }

    private int getTaskLevel(Task task) {
        int level = 0;
        for (Task dependency : adjList.get(task).getDependencyTasks()) {
            int dependencyLevel = getTaskLevel(dependency);
            level = Math.max(level, dependencyLevel + 1);
        }
        return level;
    }
}

class Task implements Runnable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return completed == task.completed && Objects.equals(name, task.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, completed);
    }

    private final String name;
    private boolean completed;

    Task(String name) {
        this.name = name;
        this.completed = false;
    }

    String getName() {
        return name;
    }

    @Override
    public void run() {
        System.out.println("开始执行任务: " + name);
        // 模拟执行任务的时间
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("任务完成: " + name);
        completed = true;
    }

    boolean isCompleted() {
        return completed;
    }
}

/**
 * 基于DAG的任务调度 + 支持同层节点并发执行
 * TODO： 这样其实属于特例，还不够完美，试想一下，假如同层的节点A1先执行了，但同层其他任务A2 A3 A4还未完成，但是B1仅仅依赖于A1，那是不是B1就可以运行而不需要等待呢？
 */
public class ConcurrentTaskScheduler {
    public static void main(String[] args) {
        DAG dag = new DAG();

        // 添加任务
        dag.addTask("start");
        dag.addTask("master-data");
        dag.addTask("product");
        dag.addTask("inventory");
        dag.addTask("coupon-center");
        dag.addTask("end");

        // 添加任务之间的依赖关系
        dag.addEdge("start", "master-data");
        dag.addEdge("master-data", "product");
        dag.addEdge("master-data", "inventory");
        dag.addEdge("product", "coupon-center");
        dag.addEdge("inventory", "coupon-center");
        dag.addEdge("coupon-center", "end");

        // 拓扑排序并启动任务
        dag.topologicalSortAndExecute();
    }
}
