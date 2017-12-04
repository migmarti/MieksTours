<?php
include_once '../con.php';
	
	class User {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		
		public function does_user_exist($email)
		{
			$query = "Select * from Users where email='$email'";
			$result = mysqli_query($this->connection, $query);
			if(mysqli_num_rows($result)>0){
				$result = $mysqli->query($query);
				$data = $result->fetch_assoc();
				echo json_encode($data);
				
				$json['error'] = 'E-mail already exists: : '.$email;
				echo json_encode($json);
				mysqli_close($this -> connection);
			}
			else{
				$json['success'] = 'Account available';
				echo json_encode($json);
				mysqli_close($this->connection);
			}
			
		}
		
	}
	
	
	$user = new User();
	if(isset($_POST['email'])) {
		$email = $_POST['email'];
		
		if(!empty($email)){
			$user-> does_user_exist($email);
			
		}else{
			echo json_encode("you must type both inputs");
		}
		
	}
?>