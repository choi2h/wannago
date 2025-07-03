import { XIcon } from '../assets/icons/Icons';
import '../assets/css/tag.css';

const getTagByType = (type, text, onClick) => {
    if(type === 'view') {
        return (
            <div className="tags">
            <div className="tag">{text}</div>
            </div>
        )
    } else if(type === 'input') {
        return (
            <div className="tag-box">
                <div className="tag-text">{text}</div>
                <div className="tag-close" onClick={onClick}>
                    <XIcon/>
                </div>
            </div>
        );
    }

    return null;
}

function Tag({type, text, onClick}) {
    return getTagByType(type, text, onClick);
}

export default Tag;