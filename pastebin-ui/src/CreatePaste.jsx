import { useState } from "react";

const API_BASE = "http://localhost:8080";

function CreatePaste() {
  const [content, setContent] = useState("");
  const [ttl, setTtl] = useState("");
  const [maxViews, setMaxViews] = useState("");
  const [resultUrl, setResultUrl] = useState("");
  const [error, setError] = useState("");

  async function createPaste() {
    setError("");
    setResultUrl("");

    const payload = {
      content: content,
    };

    if (ttl) payload.ttl_seconds = Number(ttl);
    if (maxViews) payload.max_views = Number(maxViews);

    try {
      const res = await fetch(`${API_BASE}/api/pastes`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        const err = await res.json();
        setError(err.error || "Failed to create paste");
        return;
      }

      const data = await res.json();
      setResultUrl(data.url);
    } catch (e) {
      setError("Backend not reachable");
    }
  }

  return (
    <div>
      <textarea
        rows={10}
        cols={70}
        placeholder="Enter paste content"
        value={content}
        onChange={(e) => setContent(e.target.value)}
      />

      <br /><br />

      <label>TTL (seconds): </label>
      <input
        type="number"
        min="1"
        value={ttl}
        onChange={(e) => setTtl(e.target.value)}
      />

      <br /><br />

      <label>Max views: </label>
      <input
        type="number"
        min="1"
        value={maxViews}
        onChange={(e) => setMaxViews(e.target.value)}
      />

      <br /><br />

      <button onClick={createPaste}>Create Paste</button>

      {resultUrl && (
        <>
          <br /><br />
          <b>Paste URL:</b><br />
          <a href={resultUrl} target="_blank">{resultUrl}</a>
        </>
      )}

      {error && (
        <>
          <br /><br />
          <span style={{ color: "red" }}>{error}</span>
        </>
      )}
    </div>
  );
}
export default CreatePaste;