<?php
include_once '../con.php';
	
	class User {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		public function update_request($UserId)
		{
				$query = "SELECT * FROM Users WHERE UserId = '$UserId' ";
				$result = mysqli_query($this -> connection, $query);

				while($row = $result->fetch_assoc()){
				     $json[] = $row;
				}
				$data['data'] = $json;
				echo json_encode($data);
				mysqli_close($this -> connection);
		}
	}
	
	$user = new User();
	if(isset($_POST['UserId'])) {
		$UserId = $_POST['UserId'];
		if(!empty($UserId)){
			$user-> update_request($UserId);	
		}
		else{
			echo json_encode("you must type both inputs");
		}
		
	}
?>