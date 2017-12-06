<?php
include_once '../con.php';
	
	class User {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		public function update_request($RequestId,$StatusId)
		{
				$query = "UPDATE Requests SET StatusId = '$StatusId' WHERE RequestId = '$RequestId' ";

				$result = mysqli_query($this -> connection, $query);
			
				$json['success'] = 'Updated';

				echo json_encode($json);
				mysqli_close($this -> connection);
		}
	}
	
	$user = new User();
	if(isset($_POST['RequestId'],$_POST['StatusId'])) {

		$RequestId = $_POST['RequestId'];
		$StatusId = $_POST['StatusId'];
		
		if(!empty($StatusId)){
			$user-> update_request($RequestId,$StatusId);
			
		}
		else{
			echo json_encode("you must type both inputs");
		}
		
	}
?>