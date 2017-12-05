<?php
	include_once '../con.php';
	class User {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		public function insert_int($Interests)
		{

			$temp_int = $Interests;
			$InterestArray = explode(" " , $Interests);
			$UserId = '';
			$Cont = 1;
			$resultado = count($InterestArray);

			foreach ($InterestArray as $value) {
				if ($Cont == 1) {
					$UserId = $value;
					$query = "DELETE FROM UserInterests WHERE UserId = '$UserId'";
					$result = mysqli_query($this -> connection, $query);
					echo " Added UserId " . $value;
				}
				else{
					$query = "INSERT INTO UserInterests (UserId, InterestId) VALUES ('$UserId' , '$value')";
					$result = mysqli_query($this -> connection, $query);
					echo " Added Interest " . $value;
					
				}
				$Cont = $Cont + 1;

			}
			mysqli_close($this -> connection);
		}
		
	}
	
	$user = new User();
	if(isset($_POST['Interests'])) {
		$Interests = $_POST['Interests'];
		if(!empty($Interests)){
			$user-> insert_int($Interests);
			
		}else{
			echo json_encode("You need to POST a Interests variable");
		}
		
	}






?>