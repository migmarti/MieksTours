<?php
include_once '../con.php';
	
	class User {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		
		public function get_requests($HostId)
		{

				$query = "SELECT * FROM Requests WHERE HostId = '$HostId'";

				$result = mysqli_query($this -> connection, $query);

				while($row = $result->fetch_assoc()){
				     $json[] = $row;
				}
				$data['data'] = $json;
				echo json_encode($data);

				mysqli_close($this->connection);

		}
		
	}
	
	$user = new User();
	if(isset($_POST['HostId'])) {

		$HostId = $_POST['HostId'];
	
		if(!empty($HostId)){
			$user-> get_requests($HostId);
			
		}else{
			echo json_encode("you must type both inputs");
		}
		
	}
?>