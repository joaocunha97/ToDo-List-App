<?php
include "init.php";

$task_id = $_POST['task_id'];
$query = "DELETE FROM tbl_task where task_id='$task_id'";

if(mysqli_query($conn,$query)){
    echo "deleted";
}else{
    echo "failed";
}
?>