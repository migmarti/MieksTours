<?php
include_once '../con.php';
	
	class User {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		public function convert_to_host($UserId,$startDate,$endDate,$HostingStatus)
		{
				if($HostingStatus == 1){

					$query = "UPDATE Users SET HostingStatus = '$HostingStatus' , startDate = '$startDate' , endDate = '$endDate' WHERE UserId = '$UserId'";
					$json['success'] = 'User is now host';
				}
				else{
					$query = "UPDATE Users SET HostingStatus = '$HostingStatus' , startDate = '$startDate' , endDate = '$endDate' WHERE UserId = '$UserId'";
					$json['success'] = 'User is no longer host';
				}

				$result = mysqli_query($this -> connection, $query);
				echo json_encode($json);
				mysqli_close($this -> connection);
		}
	}
	
	$user = new User();
	if(isset($_POST['UserId'])) {

		$UserId = $_POST['UserId'];
		$startDate = $_POST['startDate'];
		$endDate = $_POST['endDate'];
		$HostingStatus = $_POST['HostingStatus'];
	
		if(!empty($UserId) && !empty($startDate)){
			$user-> convert_to_host($UserId,$startDate,$endDate,$HostingStatus);
			
		}else{
			echo json_encode("you must type both inputs");
		}
		
	}
?>