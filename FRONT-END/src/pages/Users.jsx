import React, { useState, useEffect,  } from 'react'
import { addUser } from '../Services/aviationServices'
import Header from '../components/Header';

const Users = () => {
    const [email, setEmail] = useState('');
    const [fullName, setFullName] = useState('');
    const [userType, setUserType] = useState('PILOT');

    const handleSubmit = (e) => {
        e.preventDefault();
        
        const userData = {
            email: email,
            fullname: fullName,
            userType: userType,
        }
        console.log(userData)

        addUser(userData)
            .then(response => {
                console.log("User added successfully:", response.data);
            })

    };

    return (
        <div>
            <Header title="Users" />
            <div>
                <form onSubmit={handleSubmit}>
                    <div className='form-group mb-2'>
                        <label className='form-label' htmlFor="email">Email:</label>
                        <input
                            type="email"
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            className='form-control'
                            required
                        />
                    </div>
                    <div className='form-group mb-2'>
                        <label className='form-label' htmlFor="fullName">Full Name:</label>
                        <input
                            type="text"
                            id="fullName"
                            value={fullName}
                            onChange={(e) =>  setFullName(e.target.value)}
                            className='form-control'
                            required
                        />
                    </div>
                    <div className='form-group mb-2'>
                        <label className='form-label' htmlFor="userType">User Type:</label>
                        <select
                            id="userType"
                            value={userType}
                            onChange={(e) => setUserType(e.target.value)}
                            className='form-control'
                            required
                        >
                            <option value="PILOT">PILOT</option>
                            <option value="CABIN_CREW">CABIN CREW</option>
                            <option value="DISPATCHER">DISPATCHER</option>
                            <option value="AVIATION_STUDENT">AVIATION STUDENT</option>
                            <option value="OTHER">OTHER</option>
                        </select>
                    </div>
                    <button type="submit" className="btn btn-primary">Submit</button>
                </form>
            </div>
        </div>
    );
};

export default Users;
