import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const API_BASE = "http://localhost:8080";

function ViewPaste() {
  const { id } = useParams();
  const [data, setData] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch(`${API_BASE}/api/pastes/${id}`)
      .then(async res => {
        const json = await res.json();
        if (!res.ok) throw json;
        setData(json);
      })
      .catch(err => {
        setError(err.error || "Paste not found or expired");
      });
  }, [id]);

  if (error) return <p style={{ color: "red" }}>{error}</p>;
  if (!data) return <p>Loading...</p>;

  return (
    <div>
      <pre>{data.content}</pre>
      {data.remaining_views !== null && (
        <p>Remaining views: {data.remaining_views}</p>
      )}
    </div>
  );
}

export default ViewPaste;
