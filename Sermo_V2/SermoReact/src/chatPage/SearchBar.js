import { useRef } from "react";
import "./subChat.css"
function SearchBar({ doSearch }) {
    const searchInput = useRef(null);
    const search = function () {
        doSearch(searchInput.current.value);
    };

    return (
        <div className="searchRow">
            <input
                ref={searchInput}
                onKeyUp={search}
                type="text"
                className="Search-barr"
                placeholder="search"
            />
        </div>
    );
}

export default SearchBar;
