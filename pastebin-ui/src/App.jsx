import { BrowserRouter, Routes, Route } from 'react-router-dom';
import CreatePaste from './CreatePaste'
import ViewPaste from './ViewPaste'


function App() {
return (
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<CreatePaste />} />
      <Route path="/p/:id" element={<ViewPaste />} />
    </Routes>
  </BrowserRouter>
)
}

export default App
