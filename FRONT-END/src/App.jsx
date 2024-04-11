import './App.css'
import Sidebar from './components/Sidebar'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Home from './pages/Home'
import Scan from './pages/Scan'
import Tools from './pages/Tools'
import Borrowed from './pages/Borrowed'
import Report from './pages/Report'
import Users from './pages/Users'

function App() {

  return (
    <BrowserRouter>
      <div className='d-flex'>
        <div className='col-auto'>
          <Sidebar />
        </div>
        <div className='container-fluid p-0'>
          <Routes>
            <Route path='/' element={<Home />}></Route>
            <Route path='/scan' element={<Scan />}></Route>
            <Route path='/borrowed' element={<Borrowed /> }></Route>
            <Route path='/tools' element={<Tools /> }></Route>
            <Route path='/users' element={<Users /> }></Route>
            <Route path='/report' element={<Report /> }></Route>
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  )
}

export default App
