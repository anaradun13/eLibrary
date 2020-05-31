app.controller("MainController", ["$scope", "$location", "appService", 
	function($scope, $location, appService) {
	
	$(".loader").hide();
	
	$scope.visible = null;
	var librarianOp = appService.getLibrarianOp();
	var bookOp = appService.getBookOp();
	var userOp = appService.getUserOp();
	$scope.isVisibleLib = function(location) {
        if (librarianOp.indexOf(librarianOp) >= 0) {
            return true;
        }
        else {
        	return false;
        }
    };
    
    $scope.isVisibleBook = function(location) {
        if (bookOp.indexOf(librarianOp) >= 0) {
            return true;
        }
        else {
        	return false;
        }
    };
    
	$scope.isActive = function(location) {
		return location === $location.path();
	};
	
}]);

var unwantedInputs = ["/", "#", "?", "%", ";", ".", "NULL", "MERGE", "DELETE", "UPDATE", "INSERT", "SELECT"];

app.controller("AdminLogInCtrl", ['$scope', '$http', function($scope, $http) {
	
	$(".loader").hide();

	$scope.adminLogIn = function() {


		var email = document.getElementById("email").value;
        $(".loader").show();
        var password = document.getElementById("password").value;
        var button = document.getElementById("submitAdminBtn");
        button.disabled = true;
        
        for (var u = 0; u < unwantedInputs.length; u++) {
            if (email.toUpperCase().indexOf(unwantedInputs[u]) != -1) {
                $scope.errorEmptyMsg = true;
                $scope.errorMsg = false;
                $scope.admin = null;
                responseFlag = false;
            } else {
                responseFlag = true;
            }
        }
        
		if (!email) {
			$scope.errorEmptyMsg = false;
			$scope.errorMsg = true;
			$scope.admin = null;
            $(".loader").hide();
            button.disabled = false;
		} else {
			$http.get("/logIn?=email" + email + "&password=" + password).then(function(response) {
                $(".loader").hide();
                button.disabled = false;
				$scope.admin = response.data;

				$scope.errorMsg = true;
				$scope.errorEmptyMsg = true;

				if (!$scope.librarian) {
					$scope.errorMsg = false;
					$scope.errorEmptyMsg = true;
				}
				else {
					appService.setLibrarianOp(response.data);
				}

				$scope.$watch("response.data", function(newVal, oldVal, scope) {
					if (newVal) {
						scope.admin = newVal;
					}
				});
			});
		}
	}
	
}]);
	
app.controller("LibrarianLogInCtrl", ['$scope', '$http', function($scope, $http) {
	
	$(".loader").hide();

		$scope.librarianLogIn = function() {

		var email = document.getElementById("email").value;
		var password = document.getElementById("password").value;
	        $(".loader").show();
	        var button = document.getElementById("submitLibBtn");
	        button.disabled = true;
	        
	        for (var u = 0; u < unwantedInputs.length; u++) {
	            if (email.toUpperCase().indexOf(unwantedInputs[u]) != -1) {
	                $scope.errorEmptyMsg = true;
	                $scope.errorMsg = false;
	                $scope.librarian = null;
	                responseFlag = false;
	            } else {
	                responseFlag = true;
	            }
	        }
	        
			if (!email) {
				$scope.errorEmptyMsg = false;
				$scope.errorMsg = true;
				$scope.librarian = null;
	            $(".loader").hide();
	            button.disabled = false;
			} else {
				$http.get("/logIn?=email" + email + "&password=" + password).then(function(response) {
	                $(".loader").hide();
	                button.disabled = false;
					$scope.librarian = response.data;

					$scope.errorMsg = true;
					$scope.errorEmptyMsg = true;

					if (!$scope.librarian) {
						$scope.errorMsg = false;
						$scope.errorEmptyMsg = true;
					}
					else {
						appService.setLibrarianOp(response.data);
					}
					
					$scope.$watch("response.data", function(newVal, oldVal, scope) {
						if (newVal) {
							scope.librarian = newVal;
						}
					});
				});
			}
		}
			
	}]);

