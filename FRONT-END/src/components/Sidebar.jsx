import React from 'react';
import { Link } from 'react-router-dom';
import logo from '../assets/logo.jpg'
const Sidebar = () => {
  return (
    <div className='sidebar d-flex flex-column bg-dark p-4 vh-100'>
      <div>
        <a href="" className='text-white d-flex justify-content-center align-items-center'>
            <img src={logo} alt="" width={50} />
        </a>
        <hr className='text-secondary mt-2' />
        <ul className='nav nav-pills flex-column p-0 m-0'>
            <li className='nav-item p-1'>
                <Link to='/' className='nav-link text-white'>
                    <i className='bi bi-house me-2 fs-5'></i>
                    <span className='fs-5'>Home</span>
                </Link>
            </li>
            <li className='nav-item p-1'>
                <Link to='scan' className='nav-link text-white'>
                    <i className='bi bi-qr-code-scan me-2 fs-5'></i>
                    <span className='fs-5'>Scan</span>
                </Link>
            </li>
            <li className='nav-item p-1'>
                <Link to='borrowed' className='nav-link text-white'>
                    <i className='bi bi-cart me-2 fs-5'></i>
                    <span className='fs-5'>Borrowed</span>
                </Link>
            </li>
            <li className='nav-item p-1'>
                <Link to='tools' className='nav-link text-white'>
                    <i className='bi bi-view-list me-2 fs-5'></i>
                    <span className='fs-5'>Tools</span>
                </Link>
            </li>
            <li className='nav-item p-1'>
                <Link to='users' className='nav-link text-white'>
                    <i className='bi bi-view-list me-2 fs-5'></i>
                    <span className='fs-5'>Users</span>
                </Link>
            </li>
            <li className='nav-item p-1'>
                <Link to='report' className='nav-link text-white'>
                    <i className='bi bi-grid me-2 fs-5'></i>
                    <span className='fs-5'>Report</span>
                </Link>
            </li>
        </ul>
      </div>

      <div className='text-white'>
        <hr className='text-secondary' />
        <i className='bi bi-person fs-5 me-2'></i>
        <span className='fs-4'>Admin</span>
      </div>
    </div>
  );
};

export default Sidebar;
