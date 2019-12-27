# 目录

## 1 Introduction

## 2 Ergonomics
### 2.1 Garbage Collector, Heap, and Runtime Compiler Default Selections
### 2.2 Behavior-Based Tuning
#### 2.2.1 Maximum Pause Time Goal
#### 2.2.2 Throughput Goal
#### 2.2.3 Footprint Goal
### 2.3 Tuning Strategy

## 3 Generations
### 3.1 Performance Considerations
### 3.2 Measurement

## 4 Sizing the Generations
### 4.1 Total Heap
### 4.2 The Young Generation
#### 4.2.1 Survivor Space Sizing

## 5 Available Collectors
### 5.1 Selecting a Collector

## 6 The Parallel Collector
### 6.1 Generations
### 6.2 Parallel Collector Ergonomics
#### 6.2.1 Priority of Goals
#### 6.2.2 Generation Size Adjustments
#### 6.2.3 Default Heap Size
##### 6.2.3.1 Client JVM Default Initial and Maximum Heap Sizes
##### 6.2.3.2 Server JVM Default Initial and Maximum Heap Sizes
##### 6.2.3.3 Specifying Initial and Maximum Heap Sizes
### 6.3 Excessive GC Time and OutOfMemoryError
### 6.4 Measurements

## 7 The Mostly Concurrent Collectors
### 7.1 Overhead of Concurrency
### 7.2 Additional References

## 8 Concurrent Mark Sweep (CMS) Collector
### 8.1 Concurrent Mode Failure
### 8.2 Excessive GC Time and OutOfMemoryError
### 8.3 Floating Garbage
### 8.4 Pauses
### 8.5 Concurrent Phases
### 8.6 Starting a Concurrent Collection Cycle
### 8.7 Scheduling Pauses
### 8.8 Incremental Mode
#### 8.8.1 Command-Line Options
#### 8.8.2 Recommended Options
#### 8.8.3 Basic Troubleshooting
### 8.9 Measurements

## 9 Garbage-First Garbage Collector
### 9.1 Allocation (Evacuation) Failure
### 9.2 Floating Garbage
### 9.3 Pauses
### 9.4 Card Tables and Concurrent Phases
### 9.5 Starting a Concurrent Collection Cycle
### 9.6 Pause Time Goal

## 10 Garbage-First Garbage Collector Tuning
### 10.1 Garbage Collection Phases
Young Garbage Collections
Mixed Garbage Collections
Phases of the Marking Cycle
Important Defaults
How to Unlock Experimental VM Flags
Recommendations
Overflow and Exhausted Log Messages
Humongous Objects and Humongous Allocations
11 Other Considerations
Finalization and Weak, Soft, and Phantom References
Explicit Garbage Collection
Soft References
Class Metadata