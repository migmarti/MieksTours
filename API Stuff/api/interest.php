<?php
include_once '../con.php';
	
	class User {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		
		public function int_by_id($UserId)
		{
				$query = "SELECT usr.FirstName, intr.Name 
				FROM Users usr, UserInterests uxi, Interests intr 
				WHERE usr.UserId = uxi.UserId 
				AND intr.InterestId = uxi.InterestId
				AND usr.UserId = '$UserId'";

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
			$user-> int_by_id($UserId);
			
		}else{
			echo json_encode("You need to POST a UserId");
		}
		
	}
?>