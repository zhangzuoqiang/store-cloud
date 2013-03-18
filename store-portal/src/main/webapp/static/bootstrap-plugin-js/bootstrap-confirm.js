/* ============================================================
 * bootstrap-confirm.js v1.0.0
 * @author paperen<paperen@gmail.com>
 * @link
 * ============================================================ */
!function( $ ){

	"use strict"

	var Confirm = function (element, options) {
		this.init('confirm', element, options);
	}
	Confirm.prototype = {
		constructor: Confirm
		,init: function (type, element, options) {
			this.type = type;
			this.$element = $(element);
			this.options = $.extend({}, $.fn[this.type].defaults, options, this.$element.data());
			if ( typeof this.options.action == 'string') {
				if ( this.options.action == '' ) this.options.action = $(element).attr('href');
				if ( typeof this.options.action == 'undefined' ) this.options.action = document.location.href;
			}
			this.$element.on('click.' + this.type, this.options.selector, $.proxy(this.toggle, this));
			this._options = this.options;

			this.initModal();
		}
		,toggle: function(e) {
			e.preventDefault();
			var self = $(e.currentTarget)[this.type](this._options).data(this.type);
			self[self.getModal().hasClass('hide') ? 'show' : 'hide']();
		}
		,initModal: function() {
			if ( $('.confirm-modal').length ) return;
			$('body').append( this._options.template );

			var self = this;
			$('.confirm-cancelbtn').bind( 'click', function(){
				self.hide();
			} );
		}
		,getModal: function() {
			return $('.confirm-modal');
		}
		,setModalTitle: function( text ) {
			$(this.getModal()).find('.modal-header h3').html( text );
		}
		,setModalBody: function( text ) {
			$(this.getModal()).find('.modal-body').html( text );
		}
		,show: function() {
			var self = this;

			this.setModalTitle( self._options.title );
			this.setModalBody( self._options.message );

			$(this.getModal()).modal('show');

			// bind confirm
			$('.confirm-btn').unbind();
			$('.confirm-btn').bind( 'click', function(e){
				if ( typeof self._options.action == 'function' ) {
					self._options.action();
				} else {
					window.location.href = self._options.action;
				}
			});
		}
		,hide: function() {
			$(this.getModal()).modal('hide');
		}
	}

	var old = $.fn.confirm;
	$.fn.confirm = function ( option ) {
		return this.each(function () {
			var $this = $(this)
			, data = $this.data('confirm')
			, options = typeof option == 'object' && option
			if (!data) $this.data('confirm', (data = new Confirm(this, options)));
			if (typeof option == 'string') data[option]();
		});
	}
	$.fn.confirm.Constructor = Confirm;
	$.fn.confirm.defaults = {
		action : '',  
		title : '操作确认', 
		message : '确认执行操作', 
		template : '<div class="modal hide fade confirm-modal"><div class="modal-header"><h3></h3></div><div class="modal-body"></div><div class="modal-footer"><a href="#" class="btn btn-success confirm-btn">确 认</a><a href="#" class="btn confirm-cancelbtn">取 消</a></div></div>'
	}


	$.fn.confirm.noConflict = function () {
		$.fn.confirm = old;
		return this;
	}

}( window.jQuery )