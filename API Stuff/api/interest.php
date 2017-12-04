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
				$query = mysql_query("SELECT Name from Interests WHERE UserId = '$UserId'");

				$result = mysqli_query($this -> connection, $query);

				$data = $result->fetch_assoc();
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