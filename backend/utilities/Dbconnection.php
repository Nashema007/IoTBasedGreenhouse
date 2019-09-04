<?php
	/**
	 * Created by PhpStorm.
	 * User: Dell
	 * Date: 5/4/2019
	 * Time: 11:49 PM
	 */
	
	class Dbconnection {
		
		
		/**
		 * Dbconnection constructor.
		 */
		public function __construct() {
		}
		
		
		function con(){
			$localhost = "localhost";
			$db = "greenhouse";
			$username = "root";
			$password ="";
			
			
			
			$conn = new mysqli($localhost, $username, $password, $db) or exit($conn->error);
			return $conn;
		}
	}
	
	
	
	
	
	
