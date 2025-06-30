import { Xcircle1 } from '../assets/icons/Xcircle1';
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
                <Xcircle1 className="x-circle" onClick={onClick} />
            </div>
        );
    }

    return null;
}

function Tag({type, text, onClick}) {
    return getTagByType(type, text, onClick);
}

export default Tag;