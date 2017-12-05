<?php
include_once '../con.php';
	
	class User {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		
		public function does_user_exist($UserId,$email,$password)
		{
			$query = "Select * from Users where email='$email'";
			$result = mysqli_query($this->connection, $query);
			if(mysqli_num_rows($result)>0){
				$json['success'] = 'E-mail already exists: : '.$email;
				echo json_encode($json);
				mysqli_close($this -> connection);
			}else{
				$query = "INSERT INTO Users (UserId,email, password) values ('$UserId','$email','$password')";
				$inserted = mysqli_query($this -> connection, $query);
				if($inserted == 1 ){
					$json['success'] = 'Account created';
				}else{
					$json['error'] = 'Account not created';
				}
				echo json_encode($json);
				mysqli_close($this->connection);
			}
			
		}
		
	}
	
	
	$user = new User();
	if(isset($_POST['UserId'],$_POST['email'],$_POST['password'],$_POST['FirstName'],$_POST['LastName'],$_POST['DoB'],$_POST['Location'],$_POST['Description'])) {
		
		$UserId = $_POST['UserId'];
		$email = $_POST['email'];
		$password = $_POST['password'];

		if(!empty($email) && !empty($password)){
			$user-> does_user_exist($UserId,$email,$password);
			
		}else{
			echo json_encode("All fields required");
		}
		
	}
?>