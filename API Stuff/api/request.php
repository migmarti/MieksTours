<?php
include_once '../con.php';
	
	class User {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		
		public function insert_request($HostId,$TravelerId,$StartDate,$EndDate,$Comment,$StatusId)
		{
				$query = "INSERT INTO Requests (HostId, TravelerId, StartDate, EndDate, Comment, StatusId) VALUES('$HostId', '$TravelerId', '$StartDate', '$EndDate', '$Comment', '$StatusId')";
				$result = mysqli_query($this -> connection, $query);
				$json['success'] = 'Request sent';
				echo json_encode($json);
				mysqli_close($this->connection);
		}
		
	}
	
	$user = new User();
	if(isset($_POST['HostId'])) {
		$HostId = $_POST['HostId'];
		$TravelerId = $_POST['TravelerId'];
		$StartDate = $_POST['StartDate'];
		$EndDate = $_POST['EndDate'];
		$Comment = addslashes($_POST['Comment']);
		$StatusId = $_POST['StatusId'];


		if(!empty($HostId)){
			$user-> insert_request($HostId,$TravelerId,$StartDate,$EndDate,$Comment,$StatusId);
			
		}else{
			echo json_encode("You need to POST all parameters");
		}
		
	}
?>