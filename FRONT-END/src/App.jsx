import './App.css'
import Sidebar from './components/Sidebar'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Home from './pages/Home'
import Scan from './pages/Scan'
import Items from './pages/Items'
import Products from './pages/Products'
import Report from './pages/Report'
import Header from './components/Header'

function App() {

  return (
    <BrowserRouter>
      <div className='d-flex'>
        <div className='col-auto'>
          <Sidebar />
        </div>
        <div className='container-fluid p-0'>
          <div className='content'>
            <Routes>
              <Route path='/' element={<Home />}></Route>
              <Route path='/scan' element={<Scan />}></Route>
              <Route path='/items' element={<Items /> }></Route>
              <Route path='/products' element={<Products /> }></Route>
              <Route path='/report' element={<Report /> }></Route>
            </Routes>
          </div>
        </div>
      </div>
    </BrowserRouter>
  )
}

export default App
