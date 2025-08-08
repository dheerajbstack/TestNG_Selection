@task-management
Feature: Task Management System

  Background:
    Given the application is running on "http://localhost:3000"
    And the backend API is available at "http://localhost:5000"
    And I open the application in browser
    And I navigate to the "Tasks" tab

  @critical @TC011
  Scenario: Create New Task (1 TC)
    When I create a new task with title "Test Task" and priority "High" and assign to a default user 1
    And I should see a Task Added successfully notification
    Then the task should appear in the tasks list
    And Should be able to complete the "created" task

  @critical @TC012
  Scenario: Submit default uncompleted task (1TC)
    And Should be able to complete the "default" task

  @medium @TC013
  Scenario: Task Priority Display (2 TC)
    When I create tasks with different priority levels:
      | title        | priority |
      | High Task    | High     |
      | Medium Task  | Medium   |
      | Low Task     | Low      |
    Then the priority badges should display correctly
    When I create a task with title "Unassigned Task" and no user assigned
    Then the task should appear in the tasks list