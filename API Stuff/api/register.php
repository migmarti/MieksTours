<?php
include_once '../con.php';
	
	class User {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		
		public function does_user_exist($UserId,$email,$password,$FirstName,$LastName,$DoB,$Location,$Description,$Longitud,$Latitud)
		{
				$query = "INSERT INTO Users (UserId,email,password,FirstName,LastName,DoB,Location,Description,Longitud,Latitud) values ('$UserId','$email','$password','$FirstName','$LastName','$DoB','$Location','$Description','$Longitud','$Latitud')";
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
	
	$user = new User();
	if(isset($_POST['email'],$_POST['password'])) {

		$UserId = $_POST['UserId'];
		$email = $_POST['email'];
		$password = $_POST['password'];
		$FirstName = $_POST['FirstName'];
		$LastName = $_POST['LastName'];
		$DoB = $_POST['DoB'];
		$Location = $_POST['Location'];
		$Description = $_POST['Description'];
		$Latitud = $_POST['Latitud'];
		$Longitud = $_POST['Longitud'];
		
		if(!empty($email) && !empty($password)){
			$user-> does_user_exist($UserId,$email,$password,$FirstName,$LastName,$DoB,$Location,$Description,$Longitud,$Latitud);
			
		}else{
			echo json_encode("you must type both inputs");
		}
		
	}
?>