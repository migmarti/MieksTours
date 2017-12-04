<?php
include_once '../con.php';
	
	class User {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		public function edit_profile($UserId,$email,$password,$FirstName,$LastName,$DoB,$Location,$Description)
		{
				$query = "UPDATE Users SET email = '$email' , password = '$password' , FirstName = '$FirstName' , LastName = '$LastName' , DoB = '$DoB' , Location = '$Location' , Description = '$Description' WHERE UserId = '$UserId' " ;


				$result = mysqli_query($this -> connection, $query);
			
				$json['success'] = 'Account Updated';

				echo json_encode($json);
				mysqli_close($this -> connection);
		}
	}
	
	$user = new User();
	if(isset($_POST['email'],$_POST['UserId'])) {

		$UserId = $_POST['UserId'];
		$email = $_POST['email'];
		$password = $_POST['password'];
		$FirstName = $_POST['FirstName'];
		$LastName = $_POST['LastName'];
		$DoB = $_POST['DoB'];
		$Location = $_POST['Location'];
		$Description = $_POST['Description'];
		
		if(!empty($email)){
			$user-> edit_profile($UserId,$email,$password,$FirstName,$LastName,$DoB,$Location,$Description);
			
		}
		else{
			echo json_encode("you must type both inputs");
		}
		
	}
?>