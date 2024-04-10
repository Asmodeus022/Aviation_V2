import React from 'react';

const Header = ({title}) => {

    return (
        <div>
            <header>
                <nav className="navbar navbar-expand-lg bg-body-tertiary">
                    <div className="container-md">
                        <a className="navbar-brand" href="#">{title}</a>
                    </div>
                </nav>
            </header>
        </div>
    );
};

export default Header;
