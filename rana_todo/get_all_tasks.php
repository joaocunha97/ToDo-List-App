<?php
include "init.php";

$user_id = $_POST['user_id'];

$query = "SELECT * from tbl_task where user_id='$user_id'";

$result = mysqli_query($conn,$query);

$tasks = array();

if($result){
    while($row = mysqli_fetch_array($result)){
        $task = array(
            "task_id" => $row['task_id'],
            "task_name" => $row['task_name'],
            "user_id" => $row['user_id']
        );
        array_push($tasks,$task);

    }

    echo json_encode(array("tasks" => $tasks));
}



?>