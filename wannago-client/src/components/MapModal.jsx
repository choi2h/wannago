import '../assets/css/modal.css';
import SearchLocation from './SearchLocation';

function Modal ({children}) {
    return (
<div className="modal-overlay">
  <div className="modal">
    <div className="modal-content">
      {children}
    </div>
  </div>
</div>
    );
}

export default Modal;