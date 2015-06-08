(function($){
    $.Catalogs = function(element){
        this.element = element;
        
        this.addDialog = element.dialog({
            autoOpen: false,
            height: 300,
            width: 350,
            modal: true,
            buttons: {
              Add: this.doAdd.bind(this),
              Cancel: function() {
                this.addForm[ 0 ].reset();
                this.loginDialog.dialog( "close" );
              }.bind(this)
            }
          });
          this.addForm = this.addDialog.find("form").on( "submit", function( event ) {
              event.preventDefault();
              this.doAdd();
          }.bind(this));
          
          this.selection = [];
    };
    
    $.Catalogs.prototype = {
    
        showCatalogs: function() {
            
            var that = this;
            var dlg = this.addDialog;
            
            $.ajax("/api/catalogs").done(function(res){
                if(!res.success) return;
                
                this.catalogs = res.data;
                
                var c = $("#content");
                c.html("");
                
                function list(parent, data, parentId) {
                    var ul = $("<ul/>");
                    ul.appendTo(parent);
                    if(data) {
                        $.each(data, function(i, item){
                            var li = $("<li/>");
                            li.appendTo(ul);
                            
                            var chk = $("<input type='checkbox'/>").change(function(){
                                var checked = $(this).is(":checked");
                                if(checked) {
                                    that.selection.push(item.id);
                                } else {
                                    that.selection = $.makeArray($(that.selection).not([item.id]));
                                }
                                that.saveSelection();
                            }).attr("cchkid", item.id).appendTo(li);
                            
                            var a = $("<a href='#item'>" + item.name + "</a>");
                            a.click(function(){
                                that.showCatalog(item.id);
                            });
                            a.appendTo(li);
                            
                            list(li, item.subCatalogs, item.id);
                        });
                    }
                    
                    var actions = $("<li/>").appendTo(ul);
                    
                    $("<a href='#add'>[New]</a>").click(function(e){
                        dlg.parentId = parentId;
                        dlg.dialog("open");
                        e.preventDefault();
                    }).appendTo(actions);
                    
                    
                    $("<span> </span><a href='#move'>[Move]</a>").click(function(e){
                        that.moveSelected(parentId);
                        e.preventDefault();
                    }).appendTo(actions);
                }
                
                list(c, this.catalogs, null);
                
                $("<a href='#delete'>Delete selected</a>").click(function(){
                    
                    var dlg = $("<span>Are you sure?</span>").dialog({
                        resizable: false,
                        height:140,
                        modal: true,
                        buttons: {
                            "Delete all items": function() {
                                that.deleteSelected();
                                $( this ).dialog( "close" );
                            },
                            Cancel: function() {
                                $( this ).dialog( "close" );
                            }
                        }
                    });
                    
                }.bind(this)).appendTo(c);
                
                that.loadSelection();
            });
        },
        
        doAdd: function(){
            var values = this.addForm.formObject();
            values.upperCatalog = this.addDialog.parentId;
          
            $.ajax("/api/catalogs", { data : JSON.stringify(values), contentType : 'application/json', type : 'POST'}).done(function(res){
                if(res.success) {
                    this.showCatalogs();
                    this.addDialog.dialog( "close" );
                } else {
                    this.addDialog.find("#add-err").html("Add failed");
                }
            }.bind(this));
          
        },
        
        loadSelection: function(){
            // get selection from server
            $.ajax("/api/catalogs/selection", { data : JSON.stringify(this.selection), contentType : 'application/json'}).done(function(res){
                if(res.success) {
                    this.selection = res.data;
                    this.updateSelection();
                }
            }.bind(this));
        },
        
        saveSelection: function(){
            $.ajax("/api/catalogs/selection", { data : JSON.stringify(this.selection), contentType : 'application/json', type : 'POST'});
        },
        
        updateSelection: function() {
            $("#content").find("input[cchkid]").each(function(idx, item){
                item = $(item);
                if(this.selection.indexOf(+item.attr("cchkid")) != -1) {
                    item.attr("checked", true);
                } else {
                    item.attr("checked", false);
                }
            }.bind(this));
        },
        
        deleteSelected: function() {
            
            $.ajax("/api/catalogs", { data : JSON.stringify(this.selection), contentType : 'application/json', type: 'DELETE'}).done(function(res){
                if(res.success) {
                    this.showCatalogs();
                }
            }.bind(this));
            
        },
        
        moveSelected: function(newParentId) {
            var that = this;
            var dlg = $("<span>Are you sure?</span>").dialog({
                resizable: false,
                height:140,
                modal: true,
                buttons: {
                    "Move all items": function() {
                        that.doMoveSelected(newParentId);
                        $( this ).dialog( "close" );
                    },
                    Cancel: function() {
                        $( this ).dialog( "close" );
                    }
                }
            });
        },
        
        doMoveSelected: function(newParentId) {
            if(!newParentId) newParentId = 0;
            var data = $(this.selection).map(function(){
                return {id: this, upperCatalog: newParentId};
            }).get();
            
            $.ajax("/api/catalogs", { data : JSON.stringify(data), contentType : 'application/json', type: 'PUT'}).done(function(res){
                if(res.success) {
                    this.showCatalogs();
                }
            }.bind(this));
        }
    };
    
}(jQuery));
