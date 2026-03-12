import java.util.Scanner;

/**
 * Medical Equipment Maintenance and Monitoring Tool
 * 
 * This program demonstrates:
 * - Searching (linear, binary)
 * - Sorting (bubble, insertion, selection, merge, quick)
 * - Lists (singly, doubly, circular linked lists, polynomial ADT)
 * - Stacks (array and linked list, infix to postfix, balancing symbols)
 * - Queues (circular queue, deque, linked list queue)
 * - Priority Queues (binary heap)
 * - Hashing (separate chaining, rehashing)
 * 
 * All data structures are implemented from scratch.
 * Now with interactive menu for user input.
 */
public class MedicalEquipmentManager {

    // ==================== Equipment Class ====================
    static class Equipment {
        String id;           // Unique identifier (e.g., "MRI-101")
        String name;
        String lastMaintenanceDate; // YYYY-MM-DD
        int intervalDays;    // Maintenance interval in days

        public Equipment(String id, String name, String lastMaintenanceDate, int intervalDays) {
            this.id = id;
            this.name = name;
            this.lastMaintenanceDate = lastMaintenanceDate;
            this.intervalDays = intervalDays;
        }

        // Calculates next maintenance date (simplified: just adds days)
        public String getNextMaintenance() {
            // In real code, use date arithmetic; here just for demo
            return "Calc_Date";
        }

        // Status: overdue, due-soon, operational based on current date
        public String getStatus() {
            // Placeholder: return "operational" for demo
            return "operational";
        }

        @Override
        public String toString() {
            return String.format("%s (%s): Last maint %s, interval %d days",
                    name, id, lastMaintenanceDate, intervalDays);
        }
    }

    // ==================== Searching Algorithms ====================
    static class SearchAlgorithms {
        // Linear search by ID
        public static int linearSearch(Equipment[] arr, String targetId) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].id.equals(targetId)) return i;
            }
            return -1;
        }

        // Binary search (requires sorted array by ID)
        public static int binarySearch(Equipment[] arr, String targetId) {
            int left = 0, right = arr.length - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int cmp = arr[mid].id.compareTo(targetId);
                if (cmp == 0) return mid;
                if (cmp < 0) left = mid + 1;
                else right = mid - 1;
            }
            return -1;
        }
    }

    // ==================== Sorting Algorithms ====================
    static class SortAlgorithms {
        // Bubble sort by ID
        public static void bubbleSort(Equipment[] arr) {
            int n = arr.length;
            for (int i = 0; i < n-1; i++) {
                boolean swapped = false;
                for (int j = 0; j < n-i-1; j++) {
                    if (arr[j].id.compareTo(arr[j+1].id) > 0) {
                        swap(arr, j, j+1);
                        swapped = true;
                    }
                }
                if (!swapped) break;
            }
        }

        // Insertion sort by ID
        public static void insertionSort(Equipment[] arr) {
            for (int i = 1; i < arr.length; i++) {
                Equipment key = arr[i];
                int j = i - 1;
                while (j >= 0 && arr[j].id.compareTo(key.id) > 0) {
                    arr[j+1] = arr[j];
                    j--;
                }
                arr[j+1] = key;
            }
        }

        // Selection sort by ID
        public static void selectionSort(Equipment[] arr) {
            for (int i = 0; i < arr.length-1; i++) {
                int minIdx = i;
                for (int j = i+1; j < arr.length; j++) {
                    if (arr[j].id.compareTo(arr[minIdx].id) < 0) minIdx = j;
                }
                swap(arr, i, minIdx);
            }
        }

        // Merge sort by ID
        public static void mergeSort(Equipment[] arr, int l, int r) {
            if (l < r) {
                int m = l + (r - l) / 2;
                mergeSort(arr, l, m);
                mergeSort(arr, m+1, r);
                merge(arr, l, m, r);
            }
        }

        private static void merge(Equipment[] arr, int l, int m, int r) {
            int n1 = m - l + 1;
            int n2 = r - m;
            Equipment[] L = new Equipment[n1];
            Equipment[] R = new Equipment[n2];
            for (int i = 0; i < n1; i++) L[i] = arr[l + i];
            for (int j = 0; j < n2; j++) R[j] = arr[m + 1 + j];

            int i = 0, j = 0, k = l;
            while (i < n1 && j < n2) {
                if (L[i].id.compareTo(R[j].id) <= 0) arr[k++] = L[i++];
                else arr[k++] = R[j++];
            }
            while (i < n1) arr[k++] = L[i++];
            while (j < n2) arr[k++] = R[j++];
        }

        // Quick sort by ID
        public static void quickSort(Equipment[] arr, int low, int high) {
            if (low < high) {
                int pi = partition(arr, low, high);
                quickSort(arr, low, pi-1);
                quickSort(arr, pi+1, high);
            }
        }

        private static int partition(Equipment[] arr, int low, int high) {
            Equipment pivot = arr[high];
            int i = low - 1;
            for (int j = low; j < high; j++) {
                if (arr[j].id.compareTo(pivot.id) < 0) {
                    i++;
                    swap(arr, i, j);
                }
            }
            swap(arr, i+1, high);
            return i+1;
        }

        private static void swap(Equipment[] arr, int i, int j) {
            Equipment temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    // ==================== Linked Lists ====================

    // Singly Linked List for maintenance logs
    static class MaintenanceLog {
        static class Node {
            String event;
            Node next;
            Node(String event) { this.event = event; }
        }
        Node head;

        public void addEvent(String event) {
            Node newNode = new Node(event);
            newNode.next = head;
            head = newNode;
        }

        public void display() {
            Node temp = head;
            while (temp != null) {
                System.out.println(temp.event);
                temp = temp.next;
            }
        }
    }

    // Doubly Linked List for equipment history
    static class EquipmentHistory {
        static class Node {
            Equipment eq;
            Node prev, next;
            Node(Equipment eq) { this.eq = eq; }
        }
        Node head, tail;

        public void add(Equipment eq) {
            Node newNode = new Node(eq);
            if (head == null) {
                head = tail = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
        }

        public void displayForward() {
            Node temp = head;
            while (temp != null) {
                System.out.println(temp.eq);
                temp = temp.next;
            }
        }

        public void displayBackward() {
            Node temp = tail;
            while (temp != null) {
                System.out.println(temp.eq);
                temp = temp.prev;
            }
        }
    }

    // Circular Linked List for recurring maintenance tasks
    static class RecurringTasks {
        static class Node {
            String task;
            Node next;
            Node(String task) { this.task = task; }
        }
        Node tail; // tail.next is head

        public void addTask(String task) {
            Node newNode = new Node(task);
            if (tail == null) {
                tail = newNode;
                tail.next = tail;
            } else {
                newNode.next = tail.next;
                tail.next = newNode;
                tail = newNode;
            }
        }

        public void display() {
            if (tail == null) return;
            Node head = tail.next;
            Node temp = head;
            do {
                System.out.println(temp.task);
                temp = temp.next;
            } while (temp != head);
        }
    }

    // Polynomial ADT (for cost formulas)
    static class Polynomial {
        static class Term {
            int coeff;
            int exp;
            Term next;
            Term(int coeff, int exp) { this.coeff = coeff; this.exp = exp; }
        }
        Term head;

        public void addTerm(int coeff, int exp) {
            if (coeff == 0) return;
            Term newTerm = new Term(coeff, exp);
            if (head == null || exp > head.exp) {
                newTerm.next = head;
                head = newTerm;
            } else {
                Term curr = head, prev = null;
                while (curr != null && curr.exp > exp) {
                    prev = curr;
                    curr = curr.next;
                }
                if (curr != null && curr.exp == exp) {
                    curr.coeff += coeff;
                    if (curr.coeff == 0) {
                        if (prev == null) head = curr.next;
                        else prev.next = curr.next;
                    }
                } else {
                    newTerm.next = curr;
                    if (prev == null) head = newTerm;
                    else prev.next = newTerm;
                }
            }
        }

        public static Polynomial add(Polynomial p1, Polynomial p2) {
            Polynomial result = new Polynomial();
            Term t1 = p1.head, t2 = p2.head;
            while (t1 != null && t2 != null) {
                if (t1.exp == t2.exp) {
                    result.addTerm(t1.coeff + t2.coeff, t1.exp);
                    t1 = t1.next;
                    t2 = t2.next;
                } else if (t1.exp > t2.exp) {
                    result.addTerm(t1.coeff, t1.exp);
                    t1 = t1.next;
                } else {
                    result.addTerm(t2.coeff, t2.exp);
                    t2 = t2.next;
                }
            }
            while (t1 != null) { result.addTerm(t1.coeff, t1.exp); t1 = t1.next; }
            while (t2 != null) { result.addTerm(t2.coeff, t2.exp); t2 = t2.next; }
            return result;
        }

        public void display() {
            Term t = head;
            while (t != null) {
                System.out.print(t.coeff + "x^" + t.exp);
                if (t.next != null) System.out.print(" + ");
                t = t.next;
            }
            System.out.println();
        }
    }

    // ==================== Stacks ====================

    // Array-based stack
    static class ArrayStack {
        private int[] arr;
        private int top;
        private int capacity;

        public ArrayStack(int size) {
            arr = new int[size];
            capacity = size;
            top = -1;
        }

        public void push(int x) {
            if (isFull()) throw new RuntimeException("Stack overflow");
            arr[++top] = x;
        }

        public int pop() {
            if (isEmpty()) throw new RuntimeException("Stack underflow");
            return arr[top--];
        }

        public int peek() { return arr[top]; }
        public boolean isEmpty() { return top == -1; }
        public boolean isFull() { return top == capacity - 1; }
    }

    // Linked-list-based stack
    static class LinkedStack {
        class Node {
            int data;
            Node next;
            Node(int data) { this.data = data; }
        }
        Node top;

        public void push(int data) {
            Node newNode = new Node(data);
            newNode.next = top;
            top = newNode;
        }

        public int pop() {
            if (isEmpty()) throw new RuntimeException("Stack underflow");
            int data = top.data;
            top = top.next;
            return data;
        }

        public int peek() { return top.data; }
        public boolean isEmpty() { return top == null; }
    }

    // Stack applications

    // Infix to Postfix conversion
    static class InfixToPostfix {
        public static String convert(String infix) {
            StringBuilder postfix = new StringBuilder();
            java.util.Stack<Character> stack = new java.util.Stack<>(); // using Java's stack for simplicity
            for (char ch : infix.toCharArray()) {
                if (Character.isLetterOrDigit(ch)) {
                    postfix.append(ch);
                } else if (ch == '(') {
                    stack.push(ch);
                } else if (ch == ')') {
                    while (!stack.isEmpty() && stack.peek() != '(')
                        postfix.append(stack.pop());
                    stack.pop();
                } else {
                    while (!stack.isEmpty() && precedence(ch) <= precedence(stack.peek()))
                        postfix.append(stack.pop());
                    stack.push(ch);
                }
            }
            while (!stack.isEmpty()) postfix.append(stack.pop());
            return postfix.toString();
        }

        private static int precedence(char op) {
            switch (op) {
                case '+': case '-': return 1;
                case '*': case '/': return 2;
                default: return -1;
            }
        }
    }

    // Balancing Symbols (parentheses, brackets, braces)
    static class BalanceChecker {
        public static boolean isBalanced(String expr) {
            java.util.Stack<Character> stack = new java.util.Stack<>();
            for (char ch : expr.toCharArray()) {
                if (ch == '(' || ch == '[' || ch == '{') {
                    stack.push(ch);
                } else if (ch == ')' || ch == ']' || ch == '}') {
                    if (stack.isEmpty()) return false;
                    char last = stack.pop();
                    if (!matches(last, ch)) return false;
                }
            }
            return stack.isEmpty();
        }

        private static boolean matches(char open, char close) {
            return (open == '(' && close == ')') ||
                   (open == '[' && close == ']') ||
                   (open == '{' && close == '}');
        }
    }

    // ==================== Queues ====================

    // Circular Queue
    static class CircularQueue {
        private int[] arr;
        private int front, rear, size, capacity;

        public CircularQueue(int capacity) {
            this.capacity = capacity;
            arr = new int[capacity];
            front = 0;
            rear = -1;
            size = 0;
        }

        public void enqueue(int item) {
            if (isFull()) throw new RuntimeException("Queue full");
            rear = (rear + 1) % capacity;
            arr[rear] = item;
            size++;
        }

        public int dequeue() {
            if (isEmpty()) throw new RuntimeException("Queue empty");
            int item = arr[front];
            front = (front + 1) % capacity;
            size--;
            return item;
        }

        public boolean isEmpty() { return size == 0; }
        public boolean isFull() { return size == capacity; }
    }

    // Deque (double-ended queue) using array
    static class Deque {
        private int[] arr;
        private int front, rear, size, capacity;

        public Deque(int capacity) {
            this.capacity = capacity;
            arr = new int[capacity];
            front = 0;
            rear = -1;
            size = 0;
        }

        public void insertFront(int item) {
            if (isFull()) throw new RuntimeException("Deque full");
            front = (front - 1 + capacity) % capacity;
            arr[front] = item;
            size++;
            if (rear == -1) rear = front; // first element
        }

        public void insertRear(int item) {
            if (isFull()) throw new RuntimeException("Deque full");
            rear = (rear + 1) % capacity;
            arr[rear] = item;
            size++;
            if (front == -1) front = rear; // first element
        }

        public int deleteFront() {
            if (isEmpty()) throw new RuntimeException("Deque empty");
            int item = arr[front];
            front = (front + 1) % capacity;
            size--;
            if (isEmpty()) { front = 0; rear = -1; }
            return item;
        }

        public int deleteRear() {
            if (isEmpty()) throw new RuntimeException("Deque empty");
            int item = arr[rear];
            rear = (rear - 1 + capacity) % capacity;
            size--;
            if (isEmpty()) { front = 0; rear = -1; }
            return item;
        }

        public boolean isEmpty() { return size == 0; }
        public boolean isFull() { return size == capacity; }
    }

    // Linked-list-based queue
    static class LinkedQueue {
        class Node {
            int data;
            Node next;
            Node(int data) { this.data = data; }
        }
        Node front, rear;

        public void enqueue(int data) {
            Node newNode = new Node(data);
            if (rear == null) {
                front = rear = newNode;
                return;
            }
            rear.next = newNode;
            rear = newNode;
        }

        public int dequeue() {
            if (isEmpty()) throw new RuntimeException("Queue empty");
            int data = front.data;
            front = front.next;
            if (front == null) rear = null;
            return data;
        }

        public boolean isEmpty() { return front == null; }
    }

    // ==================== Priority Queue (Binary Heap) ====================
    static class MinHeap {
        private Equipment[] heap;
        private int size;
        private int capacity;

        public MinHeap(int capacity) {
            this.capacity = capacity;
            heap = new Equipment[capacity];
            size = 0;
        }

        public void insert(Equipment eq) {
            if (size == capacity) throw new RuntimeException("Heap full");
            heap[size] = eq;
            siftUp(size);
            size++;
        }

        public Equipment extractMin() {
            if (size == 0) throw new RuntimeException("Heap empty");
            Equipment min = heap[0];
            heap[0] = heap[size-1];
            size--;
            siftDown(0);
            return min;
        }

        private void siftUp(int i) {
            while (i > 0) {
                int parent = (i - 1) / 2;
                if (heap[parent].intervalDays <= heap[i].intervalDays) break; // using interval as priority
                swap(i, parent);
                i = parent;
            }
        }

        private void siftDown(int i) {
            while (2*i + 1 < size) {
                int left = 2*i + 1;
                int right = 2*i + 2;
                int smallest = left;
                if (right < size && heap[right].intervalDays < heap[left].intervalDays)
                    smallest = right;
                if (heap[i].intervalDays <= heap[smallest].intervalDays) break;
                swap(i, smallest);
                i = smallest;
            }
        }

        private void swap(int i, int j) {
            Equipment temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }

        public boolean isEmpty() { return size == 0; }
    }

    // ==================== Hashing ====================

    // Hash table with separate chaining (linked list)
    static class HashTable {
        static class HashNode {
            String key;
            Equipment value;
            HashNode next;
            HashNode(String key, Equipment value) { this.key = key; this.value = value; }
        }

        private HashNode[] buckets;
        private int capacity;
        private int size;
        private static final double LOAD_FACTOR = 0.75;

        public HashTable(int capacity) {
            this.capacity = capacity;
            buckets = new HashNode[capacity];
            size = 0;
        }

        private int hash(String key) {
            return Math.abs(key.hashCode()) % capacity;
        }

        public void put(String key, Equipment value) {
            int index = hash(key);
            HashNode head = buckets[index];
            HashNode current = head;
            while (current != null) {
                if (current.key.equals(key)) {
                    current.value = value; // update
                    return;
                }
                current = current.next;
            }
            HashNode newNode = new HashNode(key, value);
            newNode.next = head;
            buckets[index] = newNode;
            size++;
            if ((double) size / capacity > LOAD_FACTOR) {
                rehash();
            }
        }

        public Equipment get(String key) {
            int index = hash(key);
            HashNode current = buckets[index];
            while (current != null) {
                if (current.key.equals(key)) return current.value;
                current = current.next;
            }
            return null;
        }

        private void rehash() {
            int newCapacity = capacity * 2;
            HashNode[] oldBuckets = buckets;
            buckets = new HashNode[newCapacity];
            capacity = newCapacity;
            size = 0;
            for (HashNode head : oldBuckets) {
                HashNode current = head;
                while (current != null) {
                    put(current.key, current.value);
                    current = current.next;
                }
            }
        }
    }

    // ==================== Interactive Main Menu ====================
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Medical Equipment Maintenance and Monitoring Tool ===");
        System.out.println("Interactive Mode - Choose an option from the menu.\n");

        // We'll maintain a dynamic list of equipment that the user can add to.
        // Start with some sample equipment.
        java.util.ArrayList<Equipment> equipmentList = new java.util.ArrayList<>();
        equipmentList.add(new Equipment("MRI-101", "MRI Scanner", "2026-02-10", 180));
        equipmentList.add(new Equipment("CT-202", "CT Scanner", "2026-01-05", 90));
        equipmentList.add(new Equipment("XRAY-303", "X-Ray Digital", "2025-12-12", 365));
        equipmentList.add(new Equipment("ULS-404", "Ultrasound", "2026-02-20", 120));
        equipmentList.add(new Equipment("VENT-505", "Ventilator", "2026-03-01", 30));

        while (true) {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Display all equipment");
            System.out.println("2. Add new equipment");
            System.out.println("3. Search equipment (linear)");
            System.out.println("4. Search equipment (binary) – requires sorting first");
            System.out.println("5. Sort equipment (choose algorithm)");
            System.out.println("6. Demonstrate Linked Lists (maintenance log, history, recurring tasks)");
            System.out.println("7. Polynomial ADT (add two polynomials)");
            System.out.println("8. Stacks (array stack, infix to postfix, balance symbols)");
            System.out.println("9. Queues (circular queue, deque, linked queue)");
            System.out.println("10. Priority Queue (Min Heap based on interval)");
            System.out.println("11. Hashing (hash table with separate chaining)");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    displayEquipment(equipmentList);
                    break;
                case 2:
                    addEquipment(equipmentList, scanner);
                    break;
                case 3:
                    linearSearchInteractive(equipmentList, scanner);
                    break;
                case 4:
                    binarySearchInteractive(equipmentList, scanner);
                    break;
                case 5:
                    sortMenu(equipmentList, scanner);
                    break;
                case 6:
                    linkedListsDemo(scanner);
                    break;
                case 7:
                    polynomialDemo(scanner);
                    break;
                case 8:
                    stacksDemo(scanner);
                    break;
                case 9:
                    queuesDemo(scanner);
                    break;
                case 10:
                    priorityQueueDemo(equipmentList, scanner);
                    break;
                case 11:
                    hashingDemo(equipmentList, scanner);
                    break;
                case 0:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Helper methods for interactive menu

    private static void displayEquipment(java.util.ArrayList<Equipment> list) {
        if (list.isEmpty()) {
            System.out.println("No equipment in the list.");
        } else {
            System.out.println("\nEquipment List:");
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + ": " + list.get(i));
            }
        }
    }

    private static void addEquipment(java.util.ArrayList<Equipment> list, Scanner sc) {
        System.out.print("Enter equipment ID: ");
        String id = sc.nextLine();
        System.out.print("Enter equipment name: ");
        String name = sc.nextLine();
        System.out.print("Enter last maintenance date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.print("Enter maintenance interval (days): ");
        int interval = sc.nextInt();
        sc.nextLine();
        list.add(new Equipment(id, name, date, interval));
        System.out.println("Equipment added.");
    }

    private static void linearSearchInteractive(java.util.ArrayList<Equipment> list, Scanner sc) {
        System.out.print("Enter equipment ID to search (linear): ");
        String target = sc.nextLine();
        Equipment[] arr = list.toArray(new Equipment[0]);
        int index = SearchAlgorithms.linearSearch(arr, target);
        if (index >= 0) {
            System.out.println("Found at index " + index + ": " + arr[index]);
        } else {
            System.out.println("Not found.");
        }
    }

    private static void binarySearchInteractive(java.util.ArrayList<Equipment> list, Scanner sc) {
        System.out.print("Enter equipment ID to search (binary): ");
        String target = sc.nextLine();
        // Binary search requires sorted array. We'll sort a copy first.
        Equipment[] arr = list.toArray(new Equipment[0]);
        // Make a copy to avoid modifying original order
        Equipment[] copy = arr.clone();
        SortAlgorithms.quickSort(copy, 0, copy.length - 1);
        int index = SearchAlgorithms.binarySearch(copy, target);
        if (index >= 0) {
            System.out.println("Found: " + copy[index]);
        } else {
            System.out.println("Not found.");
        }
    }

    private static void sortMenu(java.util.ArrayList<Equipment> list, Scanner sc) {
        if (list.isEmpty()) {
            System.out.println("No equipment to sort.");
            return;
        }
        System.out.println("Choose sorting algorithm:");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Insertion Sort");
        System.out.println("3. Selection Sort");
        System.out.println("4. Merge Sort");
        System.out.println("5. Quick Sort");
        System.out.print("Enter choice: ");
        int algo = sc.nextInt();
        sc.nextLine();

        Equipment[] arr = list.toArray(new Equipment[0]);
        Equipment[] copy = arr.clone(); // work on a copy to preserve original order

        long start = System.nanoTime();
        switch (algo) {
            case 1: SortAlgorithms.bubbleSort(copy); System.out.println("Bubble sort completed."); break;
            case 2: SortAlgorithms.insertionSort(copy); System.out.println("Insertion sort completed."); break;
            case 3: SortAlgorithms.selectionSort(copy); System.out.println("Selection sort completed."); break;
            case 4: SortAlgorithms.mergeSort(copy, 0, copy.length-1); System.out.println("Merge sort completed."); break;
            case 5: SortAlgorithms.quickSort(copy, 0, copy.length-1); System.out.println("Quick sort completed."); break;
            default: System.out.println("Invalid choice."); return;
        }
        long end = System.nanoTime();
        System.out.println("Time taken: " + (end - start) + " ns");
        System.out.println("Sorted list:");
        for (Equipment eq : copy) System.out.println(eq);
    }

    private static void linkedListsDemo(Scanner sc) {
        System.out.println("\n--- Linked Lists Demonstration ---");
        // Singly Linked List
        MaintenanceLog log = new MaintenanceLog();
        log.addEvent("MRI-101 checked");
        log.addEvent("CT-202 calibration");
        System.out.println("Maintenance Log (Singly Linked List):");
        log.display();

        // Doubly Linked List
        EquipmentHistory history = new EquipmentHistory();
        history.add(new Equipment("D1", "Demo1", "2026-01-01", 100));
        history.add(new Equipment("D2", "Demo2", "2026-02-01", 200));
        System.out.println("\nEquipment History (Doubly Linked List) forward:");
        history.displayForward();
        System.out.println("Backward:");
        history.displayBackward();

        // Circular Linked List
        RecurringTasks tasks = new RecurringTasks();
        tasks.addTask("Monthly calibration");
        tasks.addTask("Weekly cleaning");
        tasks.addTask("Quarterly certification");
        System.out.println("\nRecurring Tasks (Circular Linked List):");
        tasks.display();
    }

    private static void polynomialDemo(Scanner sc) {
        System.out.println("\n--- Polynomial ADT Demonstration ---");
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();
        System.out.println("Enter terms for first polynomial (coeff exp), enter 0 0 to stop:");
        while (true) {
            System.out.print("coeff exp: ");
            int coeff = sc.nextInt();
            int exp = sc.nextInt();
            if (coeff == 0 && exp == 0) break;
            p1.addTerm(coeff, exp);
        }
        System.out.println("Enter terms for second polynomial (coeff exp), 0 0 to stop:");
        while (true) {
            System.out.print("coeff exp: ");
            int coeff = sc.nextInt();
            int exp = sc.nextInt();
            if (coeff == 0 && exp == 0) break;
            p2.addTerm(coeff, exp);
        }
        sc.nextLine(); // consume newline
        System.out.print("p1: "); p1.display();
        System.out.print("p2: "); p2.display();
        Polynomial sum = Polynomial.add(p1, p2);
        System.out.print("Sum: "); sum.display();
    }

    private static void stacksDemo(Scanner sc) {
        System.out.println("\n--- Stacks Demonstration ---");
        // Array stack
        ArrayStack stack = new ArrayStack(5);
        stack.push(10); stack.push(20); stack.push(30);
        System.out.println("ArrayStack pop: " + stack.pop());

        // Infix to Postfix
        System.out.print("Enter an infix expression (e.g., a+b*c): ");
        String infix = sc.nextLine();
        System.out.println("Postfix: " + InfixToPostfix.convert(infix));

        // Balancing Symbols
        System.out.print("Enter an expression with brackets to check balance: ");
        String expr = sc.nextLine();
        System.out.println("Balanced? " + BalanceChecker.isBalanced(expr));
    }

    private static void queuesDemo(Scanner sc) {
        System.out.println("\n--- Queues Demonstration ---");
        // Circular Queue
        CircularQueue cq = new CircularQueue(3);
        cq.enqueue(1); cq.enqueue(2); cq.enqueue(3);
        System.out.println("CircularQueue dequeue: " + cq.dequeue());

        // Deque
        Deque deque = new Deque(5);
        deque.insertFront(10); deque.insertRear(20); deque.insertFront(5);
        System.out.println("Deque delete front: " + deque.deleteFront());
        System.out.println("Deque delete rear: " + deque.deleteRear());

        // Linked Queue
        LinkedQueue lq = new LinkedQueue();
        lq.enqueue(100); lq.enqueue(200);
        System.out.println("LinkedQueue dequeue: " + lq.dequeue());
    }

    private static void priorityQueueDemo(java.util.ArrayList<Equipment> list, Scanner sc) {
        if (list.isEmpty()) {
            System.out.println("No equipment to insert into heap.");
            return;
        }
        MinHeap heap = new MinHeap(list.size());
        for (Equipment eq : list) {
            heap.insert(eq);
        }
        System.out.println("Min Heap (based on interval days) constructed.");
        System.out.println("Extract min (shortest interval): " + heap.extractMin().name);
    }

    private static void hashingDemo(java.util.ArrayList<Equipment> list, Scanner sc) {
        HashTable table = new HashTable(5);
        for (Equipment eq : list) {
            table.put(eq.id, eq);
        }
        System.out.print("Enter equipment ID to retrieve from hash table: ");
        String key = sc.nextLine();
        Equipment result = table.get(key);
        if (result != null) {
            System.out.println("Found: " + result);
        } else {
            System.out.println("Not found.");
        }
    }
}