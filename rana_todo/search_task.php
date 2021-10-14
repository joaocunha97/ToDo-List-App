<?php
include "init.php";

$task_name = $_POST['task_name'];

$query = "SELECT * from tbl_task where task_name='$task_name'";
$result = mysqli_query($conn,$query);

if(mysqli_num_rows($result) > 0) {
    $row = mysqli_fetch_array($result);
    
    $task_id = $row['task_id'];
    $task_name = $row['task_name'];
    $user_id = $row['user_id'];

    $task = array("task_id" => $task_id,
    "task_name" => $task_name,
    "user_id" => $user_id);
    
    echo json_encode($task);
}else{
    echo "not_found";
}

?>