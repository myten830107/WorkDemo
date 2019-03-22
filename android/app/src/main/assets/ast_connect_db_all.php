<?php

$hostname = "localhost";

$username = "id7773224_tcnr1608";

$password = "123456";

$dbname = "id7773224_allschoolthings";



// Create connection

$con = new mysqli($hostname, $username, $password, $dbname);

// Check connection

if ($con->connect_error) {

die("Connection failed: " . $con->connect_error);

}

//----------------------------------------------------------------

$con->query("SET NAMES utf8");

$con->query("SET CHARACTER SET 'UTF8';");

$con->query('SET CHARACTER_SET_CLIENT=UTF8;');

$con->query('SET CHARACTER_SET_RESULTS=UTF8;');

//----------------------------------------------------------------

$selefunc=$_REQUEST['selefunc_string'];

switch($selefunc){

//--------選擇瀏覽-----------------------------------------

case "query":

//$sql = "SELECT * FROM member";

  $sql = $_REQUEST['query_string'];

  $sql = stripslashes($sql);  //拿掉空格



  $result = $con->query($sql);

	if ($result->num_rows > 0) {

	// output data of each row

	while($row = $result->fetch_assoc()) {

	$output[] = $row;

	}

	print(json_encode($output));

	} else {

	//$output[] = $row;

	echo "0 results";

	}

	//print(json_encode($output));

	$con->close();

	break;



	//--------選擇新增-----------------------------------------

	case "insert":
	//date_start	time_start	date_end	time_end	category	reason	student_id	parent_id	teacher_id	absent_id

	$date_start=$_REQUEST['date_start'];

	$time_start=$_REQUEST['time_start'];

	$date_end=$_REQUEST['date_end'];

	$time_end=$_REQUEST['time_end'];

	$category=$_REQUEST['category'];

	$reason=$_REQUEST['reason'];

	$student_id=$_REQUEST['student_id'];

	$parent_id=$_REQUEST['parent_id'];

	$teacher_id=$_REQUEST['teacher_id'];

	$absent_id=$_REQUEST['absent_id'];



	$sql = "insert into leave_record values(DEFAULT,'$date_start','$time_start','$date_end','$time_end','$category','$reason','$student_id','$parent_id','$teacher_id','$absent_id') ";

	$flag['code']=0;

	if($row=$con->query($sql))

	{

	$flag['code']=1;

	echo"hi ok";

	}

	print(json_encode($flag));

	mysqli_close($con);

	break;



	//--------選擇更新-----------------------------------------

	case "update":

	$ch_id = $_REQUEST['id'];

	$ch_name=$_REQUEST['name'];

	$ch_grp=$_REQUEST['grp'];

	$ch_address=$_REQUEST['address'];



	$id_num = (int)$ch_id;

	$sql = "UPDATE  member SET name = '$ch_name'  , grp = '$ch_grp' , address = '$ch_address'   WHERE id = $id_num ";

	$flag['code']=0;



	if($row=$con->query($sql))  {

	$flag['code']=1;

	}else   {

	$flag['code']=0;

	}

	print(json_encode($flag));

	mysqli_close($con);

	break;



	//--------選擇刪除-----------------------------------------

	case"delete":

	$ch_id = $_REQUEST['id'];

	$id_num = (int)$ch_id;

	$sql = "DELETE From member WHERE id = $id_num" ;

	$flag['code']=0;



	if($row=$con->query($sql)) {

	echo"delete ok";

	$flag['code']=1;

	} else {

	echo"delete missing";

	$flag['code']=0;

	}

	print(json_encode($flag));

	mysqli_close($con);

	break;



}

//---------------------------------------------------------------

?>

