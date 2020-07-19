function loadEntries(callback) {
    fetch('/journal').then(response => response.json()).then((entries) => { // Fetch = RPC request to server and waits then gets response
        const entriesListElement = document.getElementById('entry-container'); //entry-container is a 'div'
        const referenceNode = document.getElementById("reference");
        entries.forEach((entry) => {
            thePage = document.createElement('div');
            thePage.className += 'bk-content';
            thePage.innerHTML = entry.message;
            entriesListElement.insertBefore(thePage, referenceNode);
        })
        callback();
    });
}

var Books = (function() {
    function init() {
        var $book = $( '#bk-list > li > div.book' ),
		$parent = $book.parent(),
		$page = $book.children( 'div.bk-page' ),
		$bookview = $parent.find( 'button.bk-bookview' );
		var $content = $page.children( 'div.bk-content' ), current = 0;
		$parent.find( 'button.bk-bookback' ).on( 'click', function() {	
		    $bookview.removeClass( 'bk-active' );
			if( $book.data( 'flip' ) ) {	
				$book.data( { opened : false, flip : false } ).removeClass( 'bk-viewback' ).addClass( 'bk-default' );
			}
			else {	
				$book.data( { opened : false, flip : true } ).removeClass( 'bk-viewinside bk-default' ).addClass( 'bk-viewback' );
			}				
		} );
		$bookview.on( 'click', function() {
			var $this = $( this );				
			if( $book.data( 'opened' ) ) {
				$this.removeClass( 'bk-active' );
				$book.data( { opened : false, flip : false } ).removeClass( 'bk-viewinside' ).addClass( 'bk-default' );
			}
			else {
				$this.addClass( 'bk-active' );
				$book.data( { opened : true, flip : false } ).removeClass( 'bk-viewback bk-default' ).addClass( 'bk-viewinside' );
				$parent.css( 'z-index', 1);
				current = 0;
				$content.removeClass( 'bk-content-current' ).eq( current ).addClass( 'bk-content-current' );
			}
		} );
		if( $content.length > 1 ) {
			var $navPrev = $( '<span class="bk-page-prev">&lt;</span>' ),
			$navNext = $( '<span class="bk-page-next">&gt;</span>' );	
			$page.append( $( '<nav></nav>' ).append( $navPrev, $navNext ) );
            $navPrev.on( 'click', function() {
				if( current > 0 ) {
					--current;
					$content.removeClass( 'bk-content-current' ).eq( current ).addClass( 'bk-content-current');
				}
				return false;
			} );
			$navNext.on( 'click', function() {
				if( current < $content.length - 1 ) {
					++current;
					$content.removeClass( 'bk-content-current' ).eq( current ).addClass( 'bk-content-current');
				}
				return false;
			} );
		}
	}
	return { init : init };
})();