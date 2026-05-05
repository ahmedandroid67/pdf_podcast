import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import {
  adminCreateUser,
  adminDeleteUser,
  adminResetPassword,
  adminToggleUserStatus,
  adminUpdateUser,
  adminBulkDeleteOldUsers,
  fetchAdminStats,
  fetchAdminUsers,
} from '../api';
import ThemeToggle from '../components/ThemeToggle';
import LanguageSelector from '../components/LanguageSelector';

// ─── Helpers ──────────────────────────────────────────────────────────────────

const PLAN_COLORS = {
  free: 'var(--plan-free)',
  basic: 'var(--plan-basic)',
  pro: 'var(--plan-pro)',
  business: 'var(--plan-business)',
};

const statCard = (icon, label, value, accent) => (
  <div
    key={label}
    style={{
      background: 'var(--surface-2)',
      border: `1px solid ${accent}44`,
      borderRadius: '14px',
      padding: '20px 24px',
      display: 'flex',
      alignItems: 'center',
      gap: '16px',
      flex: '1 1 180px',
      minWidth: '180px',
    }}
  >
    <div
      style={{
        fontSize: '1.8rem',
        width: 52,
        height: 52,
        borderRadius: '12px',
        background: `${accent}22`,
        display: 'grid',
        placeItems: 'center',
      }}
    >
      {icon}
    </div>
    <div>
      <p style={{ fontSize: '0.75rem', color: 'var(--text-3)', marginBottom: 2 }}>{label}</p>
      <p style={{ fontSize: '1.5rem', fontWeight: 700, color: accent }}>{value ?? '—'}</p>
    </div>
  </div>
);

// ─── Modal ────────────────────────────────────────────────────────────────────

function Modal({ title, onClose, children }) {
  return (
    <div
      style={{
        position: 'fixed',
        inset: 0,
        background: 'rgba(0,0,0,0.6)',
        display: 'grid',
        placeItems: 'center',
        zIndex: 1000,
        padding: 20,
      }}
    >
      <div
        style={{
          background: 'var(--surface)',
          border: '1px solid var(--border)',
          borderRadius: '16px',
          padding: '28px 32px',
          width: '100%',
          maxWidth: 480,
          boxShadow: '0 25px 50px rgba(0,0,0,0.5)',
        }}
      >
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 20 }}>
          <h2 style={{ fontFamily: "'Outfit',sans-serif", fontWeight: 700, fontSize: '1.2rem' }}>{title}</h2>
          <button onClick={onClose} style={{ background: 'none', border: 'none', color: 'var(--text-3)', fontSize: '1.4rem', cursor: 'pointer', lineHeight: 1 }}>×</button>
        </div>
        {children}
      </div>
    </div>
  );
}

// ─── UserForm ─────────────────────────────────────────────────────────────────

function UserForm({ initial = {}, onSave, loading }) {
  const [email, setEmail] = useState(initial.email || '');
  const [password, setPassword] = useState('');
  const [plan, setPlan] = useState(initial.plan || 'free');
  const [quota, setQuota] = useState(initial.daily_quota ?? 1);
  const [isAdmin, setIsAdmin] = useState(initial.is_admin || false);
  const isEdit = !!initial.id;

  const inputStyle = {
    width: '100%', background: 'var(--surface-2)', border: '1px solid var(--border)',
    borderRadius: 8, padding: '9px 12px', color: 'var(--text-1)', fontSize: '0.9rem',
    boxSizing: 'border-box',
  };
  const labelStyle = { fontSize: '0.78rem', color: 'var(--text-3)', display: 'block', marginBottom: 4, marginTop: 12 };

  const handleSubmit = (e) => {
    e.preventDefault();
    const payload = {
      email,
      plan,
      daily_quota: Number(quota),
      is_admin: isAdmin,
    };
    if (!isEdit) {
      payload.password = password;
    }
    onSave(payload);
  };

  return (
    <form onSubmit={handleSubmit}>
      <label style={labelStyle}>Email</label>
      <input
        style={inputStyle}
        type="email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        required
      />

      {!isEdit && (
        <>
          <label style={labelStyle}>Password (min 8 chars)</label>
          <input
            style={inputStyle}
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            minLength={8}
            required
          />
        </>
      )}
      <label style={labelStyle}>Plan</label>
      <select style={inputStyle} value={plan} onChange={e => setPlan(e.target.value)}>
        <option value="free">Free</option>
        <option value="basic">Basic</option>
        <option value="pro">Pro</option>
        <option value="business">Business</option>
      </select>
      <label style={labelStyle}>Daily Quota</label>
      <input style={inputStyle} type="number" min={0} max={1000} value={quota} onChange={e => setQuota(e.target.value)} required />
      <label style={{ ...labelStyle, marginTop: 14, display: 'flex', alignItems: 'center', gap: 8, cursor: 'pointer' }}>
        <input type="checkbox" checked={isAdmin} onChange={e => setIsAdmin(e.target.checked)} />
        Admin privileges
      </label>
      <button
        type="submit"
        disabled={loading}
        className="btn-primary"
        style={{ width: '100%', marginTop: 20 }}
      >
        {loading ? 'Saving…' : isEdit ? 'Save Changes' : 'Create User'}
      </button>
    </form>
  );
}

function ResetPasswordForm({ onSave, loading }) {
  const [password, setPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    onSave(password);
  };

  const inputStyle = {
    width: '100%',
    background: 'var(--surface-2)',
    border: '1px solid var(--border)',
    borderRadius: 8,
    padding: '9px 12px',
    color: 'var(--text-1)',
    fontSize: '0.9rem',
    boxSizing: 'border-box',
  };
  const labelStyle = {
    fontSize: '0.78rem',
    color: 'var(--text-3)',
    display: 'block',
    marginBottom: 4,
    marginTop: 12,
  };

  return (
    <form onSubmit={handleSubmit}>
      <label style={labelStyle}>New Password (min 8 chars)</label>
      <input
        style={inputStyle}
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        minLength={8}
        required
        autoFocus
      />
      <button
        type="submit"
        disabled={loading || password.length < 8}
        className="btn-primary"
        style={{ width: '100%', marginTop: 20 }}
      >
        {loading ? 'Resetting…' : 'Reset Password'}
      </button>
    </form>
  );
}

// ─── Main Component ───────────────────────────────────────────────────────────

export default function AdminDashboard() {
  const { user: me, logout } = useAuth();
  const [stats, setStats] = useState(null);
  const [users, setUsers] = useState([]);
  const [loadingData, setLoadingData] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  // Modal states
  const [editUser, setEditUser] = useState(null);
  const [resetPasswordUser, setResetPasswordUser] = useState(null);
  const [showAddModal, setShowAddModal] = useState(false);
  const [confirmDelete, setConfirmDelete] = useState(null);
  const [showBulkDeleteConfirm, setShowBulkDeleteConfirm] = useState(false);
  const [actionLoading, setActionLoading] = useState(false);

  const load = async () => {
    setLoadingData(true);
    try {
      const [s, u] = await Promise.all([fetchAdminStats(), fetchAdminUsers()]);
      setStats(s);
      setUsers(u);
    } catch (e) {
      setError(e.message);
    } finally {
      setLoadingData(false);
    }
  };

  useEffect(() => { load(); }, []);

  useEffect(() => {
    if (success) {
      const t = setTimeout(() => setSuccess(''), 5000);
      return () => clearTimeout(t);
    }
  }, [success]);

  const handleToggleStatus = async (u) => {
    try {
      const updated = await adminToggleUserStatus(u.id);
      setUsers((prev) => prev.map((x) => (x.id === updated.id ? updated : x)));
      setSuccess(`User ${u.email} is now ${updated.is_active ? 'Active' : 'Inactive'}.`);
    } catch (e) { setError(e.message); }
  };

  const handleDelete = async () => {
    if (!confirmDelete) return;
    setActionLoading(true);
    setSuccess('');
    setError('');
    try {
      await adminDeleteUser(confirmDelete.id);
      const deletedEmail = confirmDelete.email;
      setUsers((prev) => prev.filter((x) => x.id !== confirmDelete.id));
      setConfirmDelete(null);
      setSuccess(`User ${deletedEmail} deleted permanently.`);
      load();
    } catch (e) { setError(e.message); }
    finally { setActionLoading(false); }
  };

  const handleSaveEdit = async (payload) => {
    if (!editUser) return;
    setActionLoading(true);
    setSuccess('');
    setError('');
    try {
      const updated = await adminUpdateUser(editUser.id, payload);
      setUsers((prev) => prev.map((x) => (x.id === updated.id ? updated : x)));
      setEditUser(null);
      setSuccess(`User ${updated.email} updated successfully!`);
    } catch (e) { setError(e.message); }
    finally { setActionLoading(false); }
  };

  const handleCreate = async (payload) => {
    setActionLoading(true);
    setSuccess('');
    setError('');
    try {
      await adminCreateUser(payload);
      setShowAddModal(false);
      setSuccess(`User ${payload.email} created successfully!`);
      load();
    } catch (e) { setError(e.message); }
    finally { setActionLoading(false); }
  };

  const handleResetPassword = async (newPassword) => {
    if (!resetPasswordUser) return;
    setActionLoading(true);
    setSuccess('');
    setError('');
    try {
      await adminResetPassword(resetPasswordUser.id, newPassword);
      setSuccess(`Password for ${resetPasswordUser.email} reset successfully.`);
      setResetPasswordUser(null);
    } catch (e) {
      setError(e.message);
    } finally {
      setActionLoading(false);
    }
  };

  const handleBulkDelete = async () => {
    setActionLoading(true);
    setSuccess('');
    setError('');
    try {
      const res = await adminBulkDeleteOldUsers();
      setSuccess(res.message || 'Bulk cleanup completed successfully.');
      load();
    } catch (err) {
      setError(err.message || 'Bulk delete failed');
    } finally {
      setActionLoading(false);
      setShowBulkDeleteConfirm(false);
    }
  };

  const hdr = {
    position: 'sticky', top: 0, zIndex: 50,
    padding: '14px 32px', display: 'flex', alignItems: 'center',
    justifyContent: 'space-between', gap: 12, borderRadius: 0,
    borderTop: 'none', borderLeft: 'none', borderRight: 'none',
    backdropFilter: 'blur(16px)',
    background: 'rgba(var(--surface-rgb, 15,15,20),0.85)',
    borderBottom: '1px solid var(--border)',
  };

  return (
    <div style={{ minHeight: '100vh', background: 'radial-gradient(ellipse at 0% 0%, rgba(99,102,241,0.14) 0%, transparent 55%), var(--surface)', color: 'var(--text-1)' }}>

      {/* Header */}
      <header style={hdr}>
        <div style={{ display: 'flex', alignItems: 'center', gap: 10 }}>
          <span style={{ fontSize: '1.4rem' }}>🛡️</span>
          <span style={{ fontFamily: "'Outfit',sans-serif", fontWeight: 800, fontSize: '1.1rem', background: 'linear-gradient(135deg,#f59e0b,#ef4444)', WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent' }}>
            Admin Panel
          </span>
        </div>
        <div style={{ display: 'flex', alignItems: 'center', gap: 12 }}>
          <ThemeToggle />
          <LanguageSelector />
          <span style={{ color: 'var(--text-3)', fontSize: '0.85rem' }}>{me?.email}</span>
          <Link to="/" className="btn-secondary" style={{ fontSize: '0.8rem', padding: '6px 14px' }}>← Back to Studio</Link>
          <button className="btn-secondary" onClick={logout} style={{ fontSize: '0.8rem' }}>Log out</button>
        </div>
      </header>

      <main style={{ maxWidth: 1200, margin: '0 auto', padding: '36px 20px 80px' }}>

        {/* Error banner */}
        {error && (
          <div className="fade-up" style={{ background: 'rgba(239,68,68,0.12)', border: '1px solid rgba(239,68,68,0.3)', color: '#f87171', padding: '12px 18px', borderRadius: 10, marginBottom: 20, fontSize: '0.9rem', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <span>⚠️ {error}</span>
            <button onClick={() => setError('')} style={{ background: 'none', border: 'none', color: '#f87171', cursor: 'pointer', fontSize: '1.1rem' }}>✕</button>
          </div>
        )}

        {success && (
          <div className="fade-up" style={{ background: 'rgba(16,185,129,0.12)', border: '1px solid rgba(16,185,129,0.3)', color: '#34d399', padding: '12px 18px', borderRadius: 10, marginBottom: 20, fontSize: '0.9rem', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <span>✅ {success}</span>
            <button onClick={() => setSuccess('')} style={{ background: 'none', border: 'none', color: '#34d399', cursor: 'pointer', fontSize: '1.1rem' }}>✕</button>
          </div>
        )}

        {/* Stats Cards */}
        {loadingData ? (
          <div style={{ textAlign: 'center', padding: '80px 0', color: 'var(--text-3)' }}>Loading data…</div>
        ) : (
          <>
            <div style={{ display: 'flex', gap: 16, flexWrap: 'wrap', marginBottom: 32 }}>
              {statCard('👥', 'Total Users', stats?.total_users, '#6366f1')}
              {statCard('✅', 'Active Users', stats?.active_users, '#10b981')}
              {statCard('🎙️', 'Total Jobs Processed', stats?.total_jobs, '#f59e0b')}
              {statCard('🤖', 'Gemini Uses Today', stats?.total_gemini_uses, '#ec4899')}
              {statCard('🎫', 'Tokens Remaining', stats?.remaining_tokens?.toLocaleString(), '#f59e0b')}
            </div>

            {/* User Table */}
            <div style={{ background: 'var(--surface-2)', border: '1px solid var(--border)', borderRadius: 14, overflow: 'hidden' }}>
              <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', padding: '18px 24px', borderBottom: '1px solid var(--border)' }}>
                <h2 style={{ fontFamily: "'Outfit',sans-serif", fontWeight: 700, fontSize: '1rem' }}>
                  Users Management
                </h2>
                <div style={{ display: 'flex', gap: 10 }}>
                  <button className="btn-secondary" onClick={load} style={{ fontSize: '0.8rem' }}>🔄 Refresh</button>
                  <button className="btn-secondary" onClick={() => setShowBulkDeleteConfirm(true)} style={{ fontSize: '0.8rem' }}>🧹 Cleanup Old Accounts</button>
                  <button className="btn-primary" onClick={() => setShowAddModal(true)} style={{ fontSize: '0.8rem', padding: '7px 16px' }}>+ Add User</button>
                </div>
              </div>

              <div style={{ overflowX: 'auto' }}>
                <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: '0.85rem' }}>
                  <thead>
                    <tr style={{ background: 'var(--surface-3)' }}>
                      {['Email', 'Plan', 'Quota Used', 'Gemini Tokens', 'Status', 'Role', 'Actions'].map((h) => (
                        <th key={h} style={{ padding: '10px 16px', textAlign: 'left', color: 'var(--text-3)', fontWeight: 600, fontSize: '0.75rem', textTransform: 'uppercase', letterSpacing: '0.05em', whiteSpace: 'nowrap' }}>{h}</th>
                      ))}
                    </tr>
                  </thead>
                  <tbody>
                    {users.map((u) => (
                      <tr key={u.id} style={{ borderTop: '1px solid var(--border)' }}>
                        <td style={{ padding: '12px 16px', maxWidth: 220, overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
                          <span title={u.email}>{u.email}</span>
                          {u.is_admin && <span style={{ marginLeft: 6, background: '#f59e0b22', color: '#f59e0b', fontSize: '0.68rem', padding: '2px 6px', borderRadius: 4, fontWeight: 600 }}>ADMIN</span>}
                        </td>
                        <td style={{ padding: '12px 16px' }}>
                          <span style={{ background: `${PLAN_COLORS[u.plan] ?? '#6366f1'}22`, color: PLAN_COLORS[u.plan] ?? '#6366f1', fontSize: '0.75rem', padding: '3px 9px', borderRadius: 6, fontWeight: 600, textTransform: 'uppercase' }}>
                            {u.plan}
                          </span>
                        </td>
                        <td style={{ padding: '12px 16px' }}>
                          <span style={{ fontWeight: 600 }}>{u.used_today}</span>
                          <span style={{ color: 'var(--text-3)' }}> / {u.daily_quota}</span>
                        </td>
                        <td style={{ padding: '12px 16px' }}>
                          <div style={{ fontWeight: 600 }}>{u.tokens_used_today?.toLocaleString()}</div>
                          <div style={{ fontSize: '0.7rem', color: 'var(--text-3)' }}>Total: {u.total_tokens_used?.toLocaleString()}</div>
                        </td>
                        <td style={{ padding: '12px 16px' }}>
                          <span style={{ background: u.is_active ? '#10b98122' : '#ef444422', color: u.is_active ? '#10b981' : '#ef4444', fontSize: '0.75rem', padding: '3px 9px', borderRadius: 6, fontWeight: 600 }}>
                            {u.is_active ? 'Active' : 'Inactive'}
                          </span>
                        </td>
                        <td style={{ padding: '12px 16px', color: 'var(--text-3)' }}>{u.is_admin ? '🛡️ Admin' : '👤 User'}</td>
                        <td style={{ padding: '12px 16px' }}>
                          <div style={{ display: 'flex', gap: 6 }}>
                            <button
                              onClick={() => setEditUser(u)}
                              style={{ background: '#6366f122', border: '1px solid #6366f155', color: '#a5b4fc', borderRadius: 6, padding: '4px 10px', fontSize: '0.75rem', cursor: 'pointer' }}
                            >
                              ✏️ Edit
                            </button>
                            <button
                              onClick={() => setResetPasswordUser(u)}
                              style={{ background: '#f59e0b18', border: '1px solid #f59e0b55', color: '#fbbf24', borderRadius: 6, padding: '4px 10px', fontSize: '0.75rem', cursor: 'pointer' }}
                            >
                              🔑 Reset
                            </button>
                            <button
                              onClick={() => handleToggleStatus(u)}
                              style={{ background: u.is_active ? '#ef444418' : '#10b98118', border: `1px solid ${u.is_active ? '#ef444455' : '#10b98155'}`, color: u.is_active ? '#f87171' : '#34d399', borderRadius: 6, padding: '4px 10px', fontSize: '0.75rem', cursor: 'pointer' }}
                            >
                              {u.is_active ? '🔒 Deactivate' : '✅ Activate'}
                            </button>
                            <button
                              onClick={() => setConfirmDelete(u)}
                              style={{ background: '#ef444418', border: '1px solid #ef444455', color: '#f87171', borderRadius: 6, padding: '4px 10px', fontSize: '0.75rem', cursor: 'pointer' }}
                            >
                              🗑️ Delete
                            </button>
                          </div>
                        </td>
                      </tr>
                    ))}
                    {users.length === 0 && (
                      <tr>
                        <td colSpan={7} style={{ padding: 32, textAlign: 'center', color: 'var(--text-3)' }}>No users found.</td>
                      </tr>
                    )}
                  </tbody>
                </table>
              </div>
            </div>
          </>
        )}
      </main>

      {/* Edit User Modal */}
      {editUser && (
        <Modal title={`Edit User — ${editUser.email}`} onClose={() => setEditUser(null)}>
          <UserForm initial={editUser} onSave={handleSaveEdit} loading={actionLoading} />
        </Modal>
      )}

      {/* Reset Password Modal */}
      {resetPasswordUser && (
        <Modal title={`Reset Password — ${resetPasswordUser.email}`} onClose={() => setResetPasswordUser(null)}>
          <ResetPasswordForm onSave={handleResetPassword} loading={actionLoading} />
        </Modal>
      )}

      {/* Add User Modal */}
      {showAddModal && (
        <Modal title="Add New User" onClose={() => setShowAddModal(false)}>
          <UserForm onSave={handleCreate} loading={actionLoading} />
        </Modal>
      )}

      {/* Confirm Bulk Delete Modal */}
      {showBulkDeleteConfirm && (
        <Modal title="Cleanup Old Accounts" onClose={() => setShowBulkDeleteConfirm(false)}>
          <div style={{ padding: '20px' }}>
            <p>Are you sure you want to delete all non-admin accounts created more than 60 days ago?</p>
            <p style={{ color: '#f87171', fontWeight: 600, marginTop: 14 }}>⚠️ This action cannot be undone.</p>
            <div style={{ display: 'flex', gap: 12, marginTop: 24 }}>
              <button className="btn-secondary" onClick={() => setShowBulkDeleteConfirm(false)} style={{ flex: 1 }}>Cancel</button>
              <button 
                className="btn-primary" 
                onClick={handleBulkDelete} 
                disabled={actionLoading} 
                style={{ flex: 1, backgroundColor: '#ef4444', borderColor: '#ef4444' }}
              >
                {actionLoading ? 'Deleting...' : 'Confirm Bulk Delete'}
              </button>
            </div>
          </div>
        </Modal>
      )}

      {/* Individual Delete Confirmation Modal */}
      {confirmDelete && (
        <Modal title="Delete User?" onClose={() => setConfirmDelete(null)}>
          <div style={{ padding: '20px' }}>
            <p style={{ color: 'var(--text-2)', marginBottom: 20 }}>
              Are you sure you want to permanently delete <strong>{confirmDelete.email}</strong>? This will also delete all their jobs and audio files.
            </p>
            <div style={{ display: 'flex', gap: 10 }}>
              <button className="btn-secondary" onClick={() => setConfirmDelete(null)} style={{ flex: 1 }}>Cancel</button>
              <button
                onClick={handleDelete}
                disabled={actionLoading}
                style={{ flex: 1, background: '#ef4444', color: 'white', border: 'none', borderRadius: 10, padding: '10px 0', fontWeight: 600, cursor: 'pointer' }}
              >
                {actionLoading ? 'Deleting…' : '🗑️ Delete Permanently'}
              </button>
            </div>
          </div>
        </Modal>
      )}
    </div>
  );
}
