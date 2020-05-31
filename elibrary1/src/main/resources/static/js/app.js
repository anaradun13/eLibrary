var app = angular.module("app", ["ngRoute"]);

app.config(function($routeProvider){
    $routeProvider
    .when('/carousel',{
        templateUrl: 'views/carousel.html',
        controller: 'MainController'
    })
    .when('/AdminLogIn',{
            templateUrl: 'views/AdminLogIn.html',
            controller: 'AdminLogInCtrl'
        })
        .when('/LibrarianLogIn',{
            templateUrl: 'views/LibrarianLogIn.html',
            controller: 'LibrarianLogInCtrl'
        })
        .when('/createLibrarian',{
            templateUrl: 'createLibrarian.html',
            controller: 'createLibrarian'
        })
        .when('/deleteLibrarian',{
            templateUrl: 'deleteLibrarian.html',
            controller: 'deleteLibrarian'
        })
        .when('/getAllLibrarians',{
            templateUrl: 'getAllLibrarians.html',
            controller: 'getAllLibrarians'
        })
        .when('/viewLibrarian',{
            templateUrl: 'viewLibrarian.html',
            controller: 'viewLibrarian'
        })
        .when('/editLibrarian',{
            templateUrl: 'editLibrarian.html',
            controller: 'editLibrarian'
        })
        .when('/createBook',{
            templateUrl: 'createBook.html',
            controller: 'createBook'
        })
        .when('/deleteBook',{
            templateUrl: 'deleteBook.html',
            controller: 'deleteBook'
        })
        .when('/getAllBooks',{
            templateUrl: 'getAllBooks.html',
            controller: 'getAllBooks'
        })
        .when('/issueBook',{
            templateUrl: 'issueBook.html',
            controller: 'issueBook'
        })
        .when('/viewIssuedBooks',{
            templateUrl: 'viewIssuedBooks.html',
            controller: 'viewIssuedBooks'
        })
        .when('/viewBook',{
            templateUrl: 'viewBook.html',
            controller: 'viewBook'
        })
        .when('/returnBook',{
            templateUrl: 'returnBook.html',
            controller: 'returnBook'
        })
        .when('/editBook',{
            templateUrl: 'editBook.html',
            controller: 'editBook'
        })
        .when('/createUser',{
            templateUrl: 'createUser.html',
            controller: 'createUser'
        })
        .when('/deleteUser',{
            templateUrl: 'deleteUser.html',
            controller: 'deleteUser'
        })
        .when('/getAllUsers',{
            templateUrl: 'getAllUsers.html',
            controller: 'getAllUsers'
        })
        .when('/viewUser',{
            templateUrl: 'viewUser.html',
            controller: 'viewUser'
        })
        .when('/editUser',{
            templateUrl: 'editUser.html',
            controller: 'editUser'
        })
        .otherwise(
            { redirectTo: '/carousel'}
        );
});
        
app.factory("appService", function() {
    var librarianOp=[];
   
    function setLibrarianOp(data) {
    	librarianOp = data;
    }

    function getLibrarianOp() {
        return librarianOp;
    }
    
    var bookOp=[];
    
    function setBookOp(data) {
    	bookOp = data;
    }

    function getBookOp() {
        return bookOp;
    }
    
	var userOp=[];
    
    function setUserOp(data) {
    	userOp = data;
    }

    function getUserOp() {
        return userOp;
    }
    
    return {
        setLibrarianOp: setLibrarianOp,
        getLibrarianOp: getLibrarianOp,
        setBookOp: setBookOp,
        getBookOp: getBookOp,
        setUserOp: setUserOp,
        getUserOp: getUserOp
    }
    
});