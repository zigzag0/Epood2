(function($){
    $.Login = function(element){
        this.element = element;
        
        this.loginDialog = this.element.dialog({
            autoOpen: false,
            height: 300,
            width: 350,
            modal: true,
            buttons: {
              Login: this.doLogin.bind(this),
              Cancel: function() {
                this.loginForm[ 0 ].reset();
                this.loginDialog.dialog( "close" );
              }.bind(this)
            }
          });
        
          this.loginForm = this.loginDialog.find( "form" ).on( "submit", function( event ) {
              event.preventDefault();
              this.doLogin();
          }.bind(this));
          
          var that = this;
          
          $("#logout").click(function(){
              $.ajax("/api/logout").done(function(){
                  that.checkLogin();
              });
          });
          
          this.checkLogin();
    };
    
    $.Login.prototype = {
        
        updateUser: function(user) {
            if(user) {
                $("#login-line").show();
                $("#login-name").html(user.employee.firstName + " " + user.employee.lastName);
                if(this.initContent) this.initContent();
            } else {
                $("#login-line").hide();
                $("#login-name").html('');
                $("#content").html('');
            }
        },
        
        doLogin: function() {
          console.log(this);
          var values = this.loginForm.formObject();
          $.post("/api/login", values).done(function(res){
            if(res.success) {
              this.updateUser(res.data);
              this.loginForm[ 0 ].reset();
              this.loginDialog.dialog( "close" );
            } else {
              this.loginDialog.find("#login-err").html("Login failed");
            }
          }.bind(this));
          
          return true;
        },
     
        checkLogin: function() {
          $.ajax("/api/logincheck").done(function(res){
            if(!res.success && res.message == 'AUTH_FAIL') {
                this.updateUser(null);
                this.loginDialog.dialog("open");
            } else if(res.success) {
                this.updateUser(res.data);
            }
          }.bind(this));
        }
    };
}(jQuery));
