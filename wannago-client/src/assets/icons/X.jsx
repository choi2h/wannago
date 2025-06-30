import React from "react";

export const X = ({ className }) => {
  return (
    <svg
      className={`x ${className}`}
      fill="none"
      height="24"
      viewBox="0 0 24 24"
      width="24"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        className="path"
        d="M18 6L6 18M6 6L18 18"
        stroke="#666666"
        strokeLinecap="round"
        strokeLinejoin="round"
        strokeWidth="2.5"
      />
    </svg>
  );
};
