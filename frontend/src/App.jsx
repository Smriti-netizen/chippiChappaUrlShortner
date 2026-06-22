import { useState } from 'react'
import { shortenUrlWithAlias } from './services/api'
import './App.css'

function App() {
  const [originalUrl, setOriginalUrl] = useState('')
  const [customAlias, setCustomAlias] = useState('')
  const [result, setResult] = useState(null)
  const [error, setError] = useState(null)
  const [loading, setLoading] = useState(false)
  const [copied, setCopied] = useState(false)

  const handleSubmit = async (e) => {
    e.preventDefault()
    setLoading(true)
    setError(null)
    setResult(null)
    setCopied(false)

    try {
      const data = await shortenUrlWithAlias(originalUrl, customAlias)
      setResult(data)
    } catch (err) {
      const message =
        typeof err === 'string'
          ? err
          : err?.message || 'Failed to shorten URL. Is the backend running?'
      setError(message)
    } finally {
      setLoading(false)
    }
  }

  const copyLink = async () => {
    if (!result?.shortUrl) return
    await navigator.clipboard.writeText(result.shortUrl)
    setCopied(true)
    setTimeout(() => setCopied(false), 2000)
  }

  return (
    <div className="app">
      <header className="header">
        <h1>Chippi Chappa</h1>
        <p>Shorten long URLs into shareable links</p>
      </header>

      <form className="form" onSubmit={handleSubmit}>
        <label htmlFor="url">Long URL</label>
        <input
          id="url"
          type="url"
          placeholder="https://example.com/very/long/path"
          value={originalUrl}
          onChange={(e) => setOriginalUrl(e.target.value)}
          required
        />

        <label htmlFor="alias">Custom alias (optional)</label>
        <input
          id="alias"
          type="text"
          placeholder="my-link"
          value={customAlias}
          onChange={(e) => setCustomAlias(e.target.value)}
          pattern="[a-zA-Z0-9_-]{3,50}"
          title="3–50 characters: letters, numbers, hyphens, underscores"
        />

        <button type="submit" disabled={loading}>
          {loading ? 'Shortening…' : 'Shorten URL'}
        </button>
      </form>

      {error && <p className="message error">{error}</p>}

      {result && (
        <div className="result">
          <p className="label">Your short link</p>
          <a href={result.shortUrl} target="_blank" rel="noreferrer">
            {result.shortUrl}
          </a>
          <button type="button" className="copy-btn" onClick={copyLink}>
            {copied ? 'Copied!' : 'Copy'}
          </button>
        </div>
      )}
    </div>
  )
}

export default App
