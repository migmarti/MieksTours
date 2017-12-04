<?php
include_once '../con.php';
	
	class User {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		
		public function does_user_exist($email,$password)
		{
			$query = "Select * from Users where email='$email' and password = '$password' ";
			$result = mysqli_query($this->connection, $query);

			if(mysqli_num_rows($result)>0){
				$data = $result->fetch_assoc();
				echo json_encode($data);
				// $json['success'] = 'You are logged, Welcome:  '.$email;
				// echo json_encode($json);
				mysqli_close($this -> connection);
			}else{
				
				$json['error'] = 'Could not login';
				echo json_encode($json);
				mysqli_close($this->connection);
			}
			
		}
		
	}
	
	
	$user = new User();
	if(isset($_POST['email'],$_POST['password'])) {
		$email = $_POST['email'];
		$password = $_POST['password'];
		
		if(!empty($email) && !empty($password)){
			$user-> does_user_exist($email,$password);
			
		}else{
			echo json_encode("you must type both inputs");
		}
		
	}
?>