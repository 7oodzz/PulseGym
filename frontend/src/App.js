import React, { useState, useEffect } from 'react';
import { 
  User, LogOut, Plus, Trash2, Edit, 
  Users, Activity, CheckCircle, 
  AlertCircle, Dumbbell, Calendar, Search, XCircle,
  CreditCard, LayoutDashboard
} from 'lucide-react';
import { jwtDecode } from "jwt-decode";

// --- Constants ---
const API_BASE = "http://localhost:8080";
const THEME_MAIN = "#96342d";
const THEME_DARK = "#782620";
const THEME_LIGHT = "#fdf2f2"; 

// --- Components ---

const Toast = ({ message, type, onClose }) => {
  useEffect(() => {
    const timer = setTimeout(onClose, 3000);
    return () => clearTimeout(timer);
  }, [onClose]);

  if (!message) return null;

  return (
    <div className={`fixed bottom-8 right-8 z-50 px-6 py-4 rounded-xl shadow-2xl text-white flex items-center gap-3 transition-all transform animate-slide-in ${
      type === 'error' ? 'bg-red-600' : 'bg-emerald-600'
    }`}>
      {type === 'error' ? <AlertCircle size={24} /> : <CheckCircle size={24} />}
      <span className="font-medium">{message}</span>
    </div>
  );
};

const PulseInput = ({ ...props }) => (
  <div className="relative group">
    <input 
      {...props}
      className={`w-full bg-gray-50 border border-gray-200 text-gray-800 text-sm rounded-lg focus:ring-2 focus:border-transparent block w-full p-3 transition-all duration-300 outline-none ${props.className || ''}`}
      style={{ 
        '--tw-ring-color': `${THEME_MAIN}40`, 
      }}
    />
    <div className="absolute inset-0 rounded-lg pointer-events-none border border-transparent group-hover:border-gray-300 transition-colors" />
  </div>
);

const GlassCard = ({ children, className = "" }) => (
  <div className={`bg-white rounded-2xl shadow-sm hover:shadow-lg transition-all duration-300 border border-gray-100 overflow-hidden ${className}`}>
    {children}
  </div>
);

export default function App() {
  const [user, setUser] = useState(null); 
  const [view, setView] = useState('login'); 
  const [toast, setToast] = useState({ message: '', type: '' });

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const decoded = jwtDecode(token);
        const role = decoded.type || decoded.role || decoded.authorities[0]; 
        setUser({ token, role, username: decoded.sub });
        if (role === 'Admin') setView('admin');
        else if (role === 'Receptionist') setView('receptionist');
      } catch (e) {
        localStorage.removeItem('token');
      }
    }
  }, []);

  const showToast = (message, type = 'success') => {
    setToast({ message, type });
    setTimeout(() => setToast({ message: '', type: '' }), 3000);
  };

  const handleLogin = (token) => {
    localStorage.setItem('token', token);
    const decoded = jwtDecode(token);
    const role = decoded.type || decoded.role; 
    setUser({ token, role, username: decoded.sub });
    
    if (role === 'Admin') setView('admin');
    else if (role === 'Receptionist') setView('receptionist');
    else showToast('Unknown User Role', 'error');
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    setUser(null);
    setView('login');
    showToast('Logged out successfully');
  };

  return (
    <div className="min-h-screen font-sans text-gray-800 bg-gray-50 selection:bg-red-100 selection:text-red-900">
      <Toast message={toast.message} type={toast.type} onClose={() => setToast({ message: '', type: '' })} />
      
      {view === 'login' && <LoginPage onLogin={handleLogin} showToast={showToast} />}
      
      {view === 'admin' && user?.role === 'Admin' && (
        <AdminDashboard token={user.token} onLogout={handleLogout} showToast={showToast} />
      )}
      
      {view === 'receptionist' && user?.role === 'Receptionist' && (
        <ReceptionistDashboard token={user.token} onLogout={handleLogout} showToast={showToast} />
      )}
    </div>
  );
}

// --- Sub-Pages ---

const LoginPage = ({ onLogin, showToast }) => {
  const [formData, setFormData] = useState({ username: '', password: '' });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch(`${API_BASE}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
      });
      
      if (!res.ok) {
          const errorText = await res.text();
          throw new Error(errorText || 'Invalid Credentials');
      }
      
      const data = await res.json();
      if (data.token) {
          onLogin(data.token);
      } else {
          throw new Error('No token received');
      }

    } catch (err) {
      showToast(err.message, 'error');
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen relative overflow-hidden bg-gradient-to-br from-gray-900 to-gray-800">
      <div className="absolute top-[-10%] left-[-10%] w-[500px] h-[500px] rounded-full mix-blend-multiply filter blur-3xl opacity-20" style={{ backgroundColor: THEME_MAIN }}></div>
      <div className="absolute bottom-[-10%] right-[-10%] w-[500px] h-[500px] rounded-full mix-blend-multiply filter blur-3xl opacity-20 bg-blue-600"></div>

      <div className="bg-white/10 backdrop-blur-lg border border-white/20 p-12 rounded-3xl shadow-2xl w-[420px] relative z-10">
        <div className="flex flex-col items-center mb-8">
          <div className="p-4 rounded-2xl bg-gradient-to-br shadow-lg mb-4" style={{ backgroundImage: `linear-gradient(135deg, ${THEME_MAIN}, ${THEME_DARK})` }}>
            <Dumbbell className="w-10 h-10 text-white" />
          </div>
          <h2 className="text-4xl font-extrabold text-white tracking-tight">PulseGym</h2>
          <p className="text-gray-300 mt-2 text-sm">Management System</p>
        </div>

        <form onSubmit={handleSubmit} className="space-y-5">
          <div className="group">
            <label className="block text-xs font-semibold text-gray-300 uppercase tracking-wider mb-2 ml-1">Username</label>
            <input 
              type="text" 
              className="w-full bg-white/5 border border-white/10 text-white rounded-xl px-4 py-3 focus:bg-white/10 focus:border-white/30 outline-none transition-all placeholder-gray-500"
              placeholder="Enter your username"
              value={formData.username}
              onChange={(e) => setFormData({...formData, username: e.target.value})}
              required 
            />
          </div>
          <div className="group">
            <label className="block text-xs font-semibold text-gray-300 uppercase tracking-wider mb-2 ml-1">Password</label>
            <input 
              type="password" 
              className="w-full bg-white/5 border border-white/10 text-white rounded-xl px-4 py-3 focus:bg-white/10 focus:border-white/30 outline-none transition-all placeholder-gray-500"
              placeholder="••••••••"
              value={formData.password}
              onChange={(e) => setFormData({...formData, password: e.target.value})}
              required 
            />
          </div>
          <button 
            type="submit" 
            className="w-full text-white py-3.5 rounded-xl font-bold text-lg shadow-lg hover:shadow-xl transition-all transform hover:-translate-y-0.5 mt-2 bg-gradient-to-r"
            style={{ backgroundImage: `linear-gradient(to right, ${THEME_MAIN}, ${THEME_DARK})` }}
          >
            Log In
          </button>
        </form>
      </div>
    </div>
  );
};

const DashboardShell = ({ title, activeTab, onTabChange, onLogout, tabs, children }) => (
  <div className="flex min-h-screen bg-[#f8f9fa]">
    <aside className="w-72 bg-white border-r border-gray-100 flex flex-col fixed h-full z-20 shadow-sm">
      <div className="p-8 pb-4">
        <div className="flex items-center gap-3 mb-1">
          <div className="p-2 rounded-lg" style={{ backgroundColor: THEME_MAIN }}>
            <Activity className="text-white w-6 h-6" />
          </div>
          <h1 className="text-2xl font-bold tracking-tight text-gray-900">Pulse<span style={{ color: THEME_MAIN }}>Gym</span></h1>
        </div>
        <p className="text-xs font-semibold text-gray-400 uppercase tracking-widest ml-1">{title}</p>
      </div>

      <nav className="flex-1 px-4 space-y-1.5 mt-6">
        {tabs.map(tab => {
          const isActive = activeTab === tab.id;
          return (
            <button 
              key={tab.id}
              onClick={() => onTabChange(tab.id)} 
              className={`w-full flex items-center gap-3 px-4 py-3.5 rounded-xl text-sm font-medium transition-all duration-200 group ${
                isActive 
                  ? 'bg-red-50 text-red-700 shadow-sm' 
                  : 'text-gray-500 hover:bg-gray-50 hover:text-gray-900'
              }`}
            >
              <tab.icon size={20} style={{ color: isActive ? THEME_MAIN : '#9ca3af' }} className="transition-colors group-hover:text-gray-700" />
              {tab.label}
              {isActive && <div className="ml-auto w-1.5 h-1.5 rounded-full" style={{ backgroundColor: THEME_MAIN }} />}
            </button>
          )
        })}
      </nav>

      <div className="p-4 mt-auto border-t border-gray-100">
        <button 
          onClick={onLogout} 
          className="w-full flex items-center gap-3 px-4 py-3 rounded-xl text-sm font-medium text-gray-500 hover:bg-red-50 hover:text-red-600 transition-colors"
        >
          <LogOut size={20} /> Logout
        </button>
      </div>
    </aside>

    <main className="flex-1 ml-72 p-10 overflow-y-auto min-h-screen">
      <div className="max-w-7xl mx-auto animate-fade-in">
        {children}
      </div>
    </main>
  </div>
);

const AdminDashboard = ({ token, onLogout, showToast }) => {
  const [activeTab, setActiveTab] = useState('subs'); 
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [isFormOpen, setIsFormOpen] = useState(false);
  const [formData, setFormData] = useState({});
  const [editId, setEditId] = useState(null);

  const authFetch = async (url, options = {}) => {
    const headers = { ...options.headers, 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' };
    const res = await fetch(`${API_BASE}${url}`, { ...options, headers });
    if (!res.ok) {
        const text = await res.text();
        throw new Error(text || 'API Request Failed');
    }
    return res; 
  };

  const loadData = async () => {
    setLoading(true);
    try {
      let endpoint = '';
      if (activeTab === 'subs') endpoint = '/admin/subscriptionBundles/listSubBundles';
      if (activeTab === 'sessions') endpoint = '/admin/sessionBundles';
      if (activeTab === 'receps') endpoint = '/admin/';
      if (activeTab === 'admins') endpoint = '/admin/listAdmins';

      const res = await authFetch(endpoint);
      const jsonData = await res.json();
      setData(jsonData);
    } catch (err) {
      showToast('Failed to load data: ' + err.message, 'error');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { loadData(); }, [activeTab]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      let url = '', method = '';
      if (activeTab === 'subs') {
        method = editId ? 'PUT' : 'POST';
        url = editId ? `/admin/subscriptionBundles/updateSubBundle/${editId}` : `/admin/subscriptionBundles/createSubBundle`;
      } else if (activeTab === 'sessions') {
        method = editId ? 'PUT' : 'POST';
        url = editId ? `/admin/sessionBundles/updateSessionBundle/${editId}` : `/admin/sessionBundles/createSessionBundle`;
      } else if (activeTab === 'receps') {
        method = editId ? 'PUT' : 'POST';
        url = editId ? `/admin/updateRecep/${editId}` : `/admin/addRecep`;
      } else if (activeTab === 'admins') {
        method = 'POST'; 
        url = `/admin/createAdmin`;
      }

      await authFetch(url, { method, body: JSON.stringify(formData) });
      showToast(`${editId ? 'Updated' : 'Created'} successfully`);
      setIsFormOpen(false);
      setEditId(null);
      setFormData({});
      loadData();
    } catch (err) {
      showToast(err.message, 'error');
    }
  };

  const handleDelete = async (id) => {
    if(!window.confirm("Are you sure?")) return;
    try {
      let url = '';
      if (activeTab === 'subs') url = `/admin/subscriptionBundles/deleteSubBundle/${id}`;
      if (activeTab === 'sessions') url = `/admin/sessionBundles/deleteSessionBundle/${id}`;
      if (activeTab === 'receps') url = `/admin/deleteRecep/${id}`; 
      if (activeTab === 'admins') url = `/admin/deleteAdmin/${id}`;

      await authFetch(url, { method: 'DELETE' }); 
      showToast('Deleted successfully');
      loadData();
    } catch (err) {
      showToast(err.message, 'error');
    }
  };

  const openEdit = (item) => {
    const formItem = { ...item };
    formItem.password = ""; 
    setFormData(formItem);
    setEditId(activeTab === 'receps' ? item.ssn : item.id); 
    setIsFormOpen(true);
  };

  const tabs = [
    { id: 'subs', label: 'Subscriptions', icon: CreditCard },
    { id: 'sessions', label: 'Session Bundles', icon: Dumbbell },
    { id: 'receps', label: 'Receptionists', icon: Users },
    { id: 'admins', label: 'Administrators', icon: User },
  ];

  return (
    <DashboardShell title="Admin" activeTab={activeTab} onTabChange={setActiveTab} onLogout={onLogout} tabs={tabs}>
      <div className="flex justify-between items-end mb-10">
        <div>
          <h2 className="text-3xl font-extrabold text-gray-900 capitalize tracking-tight">{tabs.find(t => t.id === activeTab)?.label}</h2>
          <p className="text-gray-500 mt-2">Manage your gym's {activeTab === 'receps' ? 'staff' : 'offerings'} and configuration.</p>
        </div>
        <button 
          onClick={() => { setFormData({}); setEditId(null); setIsFormOpen(true); }} 
          className="text-white px-5 py-3 rounded-xl flex items-center gap-2 shadow-lg shadow-red-200 hover:shadow-xl transition-all transform hover:-translate-y-0.5 font-semibold text-sm"
          style={{ backgroundColor: THEME_MAIN }}
        >
          <Plus size={18} /> Add New
        </button>
      </div>

      {loading ? (
        <div className="flex justify-center items-center h-64">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2" style={{ borderColor: THEME_MAIN }}></div>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {data.map((item, idx) => (
            <GlassCard key={idx}>
              <div className="p-6">
                <div className="flex justify-between items-start mb-4">
                  <div className="p-3 bg-red-50 rounded-xl">
                    {activeTab === 'subs' && <CreditCard size={24} style={{ color: THEME_MAIN }} />}
                    {activeTab === 'sessions' && <Dumbbell size={24} style={{ color: THEME_MAIN }} />}
                    {(activeTab === 'receps' || activeTab === 'admins') && <User size={24} style={{ color: THEME_MAIN }} />}
                  </div>
                  <div className="flex gap-1">
                    {activeTab !== 'admins' && (
                      <button onClick={() => openEdit(item)} className="p-2 hover:bg-gray-100 rounded-lg text-gray-400 hover:text-blue-600 transition">
                        <Edit size={18} />
                      </button>
                    )}
                    <button onClick={() => handleDelete(activeTab === 'receps' ? item.ssn : item.id)} className="p-2 hover:bg-red-50 rounded-lg text-gray-400 hover:text-red-600 transition">
                      <Trash2 size={18} />
                    </button>
                  </div>
                </div>
                
                <h3 className="font-bold text-xl text-gray-900 mb-1">{item.name || item.title || item.username}</h3>
                
                <div className="space-y-2 mt-4">
                  {item.price !== undefined && (
                    <p className="text-2xl font-bold tracking-tight" style={{ color: THEME_MAIN }}>
                      {item.price} <span className="text-sm font-medium text-gray-500">EGP</span>
                    </p>
                  )}
                  {item.durationInMonth && (
                    <div className="flex items-center gap-2 text-sm text-gray-600">
                      <Calendar size={16} />
                      <span>{item.durationInMonth} Months Duration</span>
                    </div>
                  )}
                  {item.sessionsAmount && (
                    <div className="flex items-center gap-2 text-sm text-gray-600">
                      <Activity size={16} />
                      <span>{item.sessionsAmount} Sessions included</span>
                    </div>
                  )}
                  {item.longTermGaol && <p className="text-sm text-gray-600 bg-gray-50 px-3 py-1.5 rounded-lg inline-block">{item.longTermGaol}</p>}
                  {item.ssn && <p className="text-xs font-mono text-gray-400">SSN: {item.ssn}</p>}
                  {item.description && <p className="text-sm text-gray-500 mt-2 line-clamp-2">{item.description}</p>}
                </div>
              </div>
            </GlassCard>
          ))}
        </div>
      )}

      {/* Modal */}
      {isFormOpen && (
        <div className="fixed inset-0 bg-gray-900/60 flex items-center justify-center z-50 animate-fade-in">
          <div className="bg-white p-8 rounded-3xl shadow-2xl w-[450px] transform transition-all scale-100">
            <h3 className="text-2xl font-bold mb-8 text-gray-900">{editId ? 'Edit' : 'Create New'}</h3>
            <form onSubmit={handleSubmit} className="space-y-5">
              {activeTab === 'subs' && (
                <>
                  <PulseInput placeholder="Bundle Name" value={formData.name || ''} onChange={e => setFormData({...formData, name: e.target.value})} required />
                  <PulseInput type="number" placeholder="Price (EGP)" value={formData.price || ''} onChange={e => setFormData({...formData, price: e.target.value})} required />
                  <PulseInput placeholder="Short Description" value={formData.description || ''} onChange={e => setFormData({...formData, description: e.target.value})} />
                  <PulseInput type="number" placeholder="Duration (Months)" value={formData.durationInMonth || ''} onChange={e => setFormData({...formData, durationInMonth: e.target.value})} required />
                </>
              )}
              {activeTab === 'sessions' && (
                <>
                  <PulseInput placeholder="Bundle Title" value={formData.title || ''} onChange={e => setFormData({...formData, title: e.target.value})} required />
                  <PulseInput placeholder="Goal (e.g. Weight Loss)" value={formData.longTermGaol || ''} onChange={e => setFormData({...formData, longTermGaol: e.target.value})} />
                  <PulseInput type="number" placeholder="Number of Sessions" value={formData.sessionsAmount || ''} onChange={e => setFormData({...formData, sessionsAmount: e.target.value})} required />
                </>
              )}
              {(activeTab === 'receps') && (
                <>
                  <PulseInput placeholder="Full Name" value={formData.name || ''} onChange={e => setFormData({...formData, name: e.target.value})} required />
                  <PulseInput placeholder="SSN (ID Number)" value={formData.ssn || ''} onChange={e => setFormData({...formData, ssn: e.target.value})} required />
                  <PulseInput placeholder="Login Username" value={formData.username || ''} onChange={e => setFormData({...formData, username: e.target.value})} required />
                  <PulseInput type="password" placeholder="Password (Leave blank to keep)" value={formData.password || ''} onChange={e => setFormData({...formData, password: e.target.value})} required={!editId} />
                </>
              )}
              {(activeTab === 'admins') && (
                 <>
                 <PulseInput placeholder="Username" value={formData.username || ''} onChange={e => setFormData({...formData, username: e.target.value})} required />
                 <PulseInput type="password" placeholder="Password" value={formData.password || ''} onChange={e => setFormData({...formData, password: e.target.value})} required />
               </>
              )}
              <div className="flex gap-4 pt-6">
                <button type="submit" className="flex-1 text-white py-3 rounded-xl font-bold hover:shadow-lg transition transform hover:-translate-y-0.5" style={{ backgroundColor: THEME_MAIN }}>Save Changes</button>
                <button type="button" onClick={() => setIsFormOpen(false)} className="flex-1 bg-gray-100 text-gray-600 py-3 rounded-xl font-bold hover:bg-gray-200 transition">Cancel</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </DashboardShell>
  );
};

const ReceptionistDashboard = ({ token, onLogout, showToast }) => {
  const [activeTab, setActiveTab] = useState('members');
  const [members, setMembers] = useState([]);
  const [filteredMembers, setFilteredMembers] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  
  const [subBundles, setSubBundles] = useState([]);
  const [sessionBundles, setSessionBundles] = useState([]);
  
  const [assignmentMemberId, setAssignmentMemberId] = useState('');
  const [assignmentItemId, setAssignmentItemId] = useState('');
  
  const [checkInId, setCheckInId] = useState('');
  const [recentAttendances, setRecentAttendances] = useState([]); 

  const [newMember, setNewMember] = useState({ name: '', phoneNo: '' });
  const [activeSubscriptions, setActiveSubscriptions] = useState([]);

  // --- Additions for Edit Member Functionality ---
  const [isEditMemberOpen, setIsEditMemberOpen] = useState(false);
  const [editMemberData, setEditMemberData] = useState({});

  const authFetch = async (url, options = {}) => {
    const headers = { ...options.headers, 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' };
    const res = await fetch(`${API_BASE}${url}`, { ...options, headers });
    if (!res.ok) {
        const text = await res.text();
        throw new Error(text || 'Action Failed');
    }
    return res;
  };

  const loadData = async () => {
    try {
      const memRes = await authFetch('/receptionist/member/listMembers');
      const memData = await memRes.json();
      setMembers(memData);
      setFilteredMembers(memData);
      
      try {
        const subRes = await authFetch('/admin/subscriptionBundles/listSubBundles');
        setSubBundles(await subRes.json());
        const sessRes = await authFetch('/admin/sessionBundles');
        setSessionBundles(await sessRes.json());
      } catch (e) { console.warn("Could not load bundles list", e); }

      if (activeTab === 'activeSubs') {
         const activeSubRes = await authFetch('/receptionist/memberSubscription/listMemberSubscriptions');
         setActiveSubscriptions(await activeSubRes.json());
      }

    } catch (err) {
      showToast('Error loading data', 'error');
    }
  };

  useEffect(() => { loadData(); }, [activeTab]);

  useEffect(() => {
    if (!searchQuery) setFilteredMembers(members);
    else {
      setFilteredMembers(members.filter(m => 
        m.phoneNo.includes(searchQuery) || 
        m.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
        String(m.id).includes(searchQuery)
      ));
    }
  }, [searchQuery, members]);

  const handleAddMember = async (e) => {
    e.preventDefault();
    const phoneRegex = /^01[0125][0-9]{8}$/;
    if (!phoneRegex.test(newMember.phoneNo)) {
      showToast('Invalid Egyptian Phone Number', 'error');
      return;
    }
    try {
      await authFetch('/receptionist/member/addMember', { 
        method: 'POST', 
        body: JSON.stringify(newMember) 
      });
      showToast('Member Added');
      setNewMember({ name: '', phoneNo: '' });
      loadData();
    } catch (err) { showToast(err.message, 'error'); }
  };

  // --- Logic for Editing a Member ---
  const openEditMember = (member) => {
    setEditMemberData({ ...member });
    setIsEditMemberOpen(true);
  };

  const handleUpdateMember = async (e) => {
    e.preventDefault();
    try {
      // The endpoint '/receptionist/member/addMember' with PUT method is mapped to updateMember in controller
      await authFetch('/receptionist/member/addMember', { 
        method: 'PUT', 
        body: JSON.stringify(editMemberData) 
      });
      showToast('Member Updated');
      setIsEditMemberOpen(false);
      loadData();
    } catch (err) { showToast(err.message, 'error'); }
  };

  const handleAssign = async (type) => {
    if (!assignmentMemberId || !assignmentItemId) {
      showToast('Please enter both Member ID and Bundle ID', 'error');
      return;
    }

    const endpoint = type === 'sub' 
      ? '/receptionist/memberSubscription/addMemberSubscription'
      : '/receptionist/memberSession/addMemberSession';

    const body = { memberId: parseInt(assignmentMemberId), itemId: parseInt(assignmentItemId) };

    try {
      await authFetch(endpoint, { method: 'POST', body: JSON.stringify(body) });
      showToast(`Assigned Successfully`);
      setAssignmentItemId('');
      setAssignmentMemberId('');
      loadData();
    } catch (err) { showToast(err.message, 'error'); }
  };

  const handleCheckIn = async (e) => {
    e.preventDefault();
    try {
      const res = await authFetch(`/receptionist/check-in/${checkInId}`, { method: 'POST' });
      const msg = await res.text();
      showToast(msg);
      
      const member = members.find(m => m.id === parseInt(checkInId));
      const newRecord = {
        memberId: checkInId,
        memberName: member ? member.name : 'Unknown',
        time: new Date().toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'}),
        status: msg
      };
      setRecentAttendances([newRecord, ...recentAttendances]);
      setCheckInId('');
    } catch (err) { showToast(err.message, 'error'); }
  };

  const handleTerminateSub = async (subId) => {
      if(!window.confirm("Terminate this subscription?")) return;
      try {
          await authFetch(`/receptionist/memberSubscription/removeMemberSubscribtion/${subId}`, { method: 'DELETE' });
          showToast("Subscription Terminated");
          loadData();
      } catch (err) { showToast(err.message, 'error'); }
  }

  const tabs = [
    { id: 'members', label: 'Members Directory', icon: Users },
    { id: 'assignSub', label: 'Assign Subscription', icon: CreditCard },
    { id: 'assignSess', label: 'Assign Private Session', icon: Dumbbell },
    { id: 'activeSubs', label: 'Active Subscriptions', icon: Activity },
    { id: 'checkin', label: 'Attendance Check-in', icon: Calendar },
  ];

  return (
    <DashboardShell title="Reception" activeTab={activeTab} onTabChange={setActiveTab} onLogout={onLogout} tabs={tabs}>
      <h2 className="text-3xl font-extrabold text-gray-900 mb-8 capitalize tracking-tight">{tabs.find(t => t.id === activeTab)?.label}</h2>

      {activeTab === 'members' && (
        <div className="space-y-8 animate-fade-in">
          {/* Add Member Card */}
          <GlassCard className="p-8 border-l-8" style={{ borderLeftColor: THEME_MAIN }}>
              <div className="flex items-center gap-3 mb-6">
                <div className="bg-red-50 p-2 rounded-lg">
                  <User size={24} style={{ color: THEME_MAIN }} />
                </div>
                <h3 className="text-xl font-bold text-gray-800">Quick Add Member</h3>
              </div>
              <form onSubmit={handleAddMember} className="flex gap-4">
                  <div className="flex-1">
                    <PulseInput placeholder="Full Name" value={newMember.name} onChange={e=>setNewMember({...newMember, name: e.target.value})} required />
                  </div>
                  <div className="flex-1">
                    <PulseInput placeholder="Phone (01xxxxxxxxx)" value={newMember.phoneNo} onChange={e=>setNewMember({...newMember, phoneNo: e.target.value})} required />
                  </div>
                  <button type="submit" className="text-white px-8 py-2 rounded-lg font-bold hover:shadow-lg transition" style={{ backgroundColor: THEME_MAIN }}>Add Member</button>
              </form>
          </GlassCard>
          
          {/* List Card */}
          <div className="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
              <div className="p-6 border-b border-gray-100 flex justify-between items-center bg-gray-50/50">
                <h3 className="font-bold text-gray-700">Member Directory</h3>
                <div className="relative w-72">
                  <Search className="absolute left-3 top-2.5 text-gray-400" size={18} />
                  <input 
                      type="text"
                      placeholder="Search ID, name or phone..."
                      className="w-full pl-10 pr-4 py-2 bg-white border border-gray-200 rounded-lg text-sm focus:ring-2 outline-none transition-all"
                      style={{ '--tw-ring-color': `${THEME_MAIN}40` }}
                      value={searchQuery}
                      onChange={e => setSearchQuery(e.target.value)}
                  />
                </div>
              </div>
              <table className="w-full text-left">
                  <thead className="bg-gray-50 text-gray-500 text-xs uppercase font-semibold tracking-wider">
                      <tr>
                          <th className="p-5">ID</th>
                          <th className="p-5">Name</th>
                          <th className="p-5">Phone</th>
                          <th className="p-5 text-center">Subscribed</th>
                          <th className="p-5 text-center">Sessions</th>
                          <th className="p-5 text-right">Actions</th>
                      </tr>
                  </thead>
                  <tbody className="divide-y divide-gray-50">
                      {filteredMembers.map(m => (
                          <tr key={m.id} className="hover:bg-red-50/30 transition duration-150 group">
                              <td className="p-5 font-mono text-gray-400 group-hover:text-gray-600">#{m.id}</td>
                              <td className="p-5 font-medium text-gray-900">{m.name}</td>
                              <td className="p-5 text-gray-600">{m.phoneNo}</td>
                              <td className="p-5 text-center">
                                {m.hasSubscription ? <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-800">Active</span> : <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-gray-100 text-gray-800">None</span>}
                              </td>
                              <td className="p-5 text-center">
                                {m.hasSession ? <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-blue-100 text-blue-800">Active</span> : <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-gray-100 text-gray-800">None</span>}
                              </td>
                              <td className="p-5 text-right">
                                <button onClick={() => openEditMember(m)} className="text-gray-400 hover:text-blue-600 transition">
                                  <Edit size={18} />
                                </button>
                              </td>
                          </tr>
                      ))}
                      {filteredMembers.length === 0 && <tr><td colSpan="6" className="p-8 text-center text-gray-400">No members found matching your search.</td></tr>}
                  </tbody>
              </table>
          </div>
        </div>
      )}

      {/* Edit Member Modal */}
      {isEditMemberOpen && (
        <div className="fixed inset-0 bg-gray-900/60 flex items-center justify-center z-50 animate-fade-in">
          <div className="bg-white p-8 rounded-3xl shadow-2xl w-[450px] transform transition-all scale-100">
            <h3 className="text-2xl font-bold mb-8 text-gray-900">Edit Member</h3>
            <form onSubmit={handleUpdateMember} className="space-y-5">
              <div className="group">
                <label className="block text-xs font-semibold text-gray-500 uppercase tracking-wider mb-1 ml-1">Full Name</label>
                <PulseInput 
                  value={editMemberData.name || ''} 
                  onChange={e => setEditMemberData({...editMemberData, name: e.target.value})} 
                  required 
                />
              </div>
              <div className="group">
                <label className="block text-xs font-semibold text-gray-500 uppercase tracking-wider mb-1 ml-1">Phone Number</label>
                <PulseInput 
                  value={editMemberData.phoneNo || ''} 
                  onChange={e => setEditMemberData({...editMemberData, phoneNo: e.target.value})} 
                  required 
                />
              </div>
              {/* Optional: Add checkboxes for subscription status if you want manual override */}
              
              <div className="flex gap-4 pt-6">
                <button type="submit" className="flex-1 text-white py-3 rounded-xl font-bold hover:shadow-lg transition transform hover:-translate-y-0.5" style={{ backgroundColor: THEME_MAIN }}>Update Member</button>
                <button type="button" onClick={() => setIsEditMemberOpen(false)} className="flex-1 bg-gray-100 text-gray-600 py-3 rounded-xl font-bold hover:bg-gray-200 transition">Cancel</button>
              </div>
            </form>
          </div>
        </div>
      )}

      {(activeTab === 'assignSub' || activeTab === 'assignSess') && (
           <div className="flex justify-center pt-10 animate-fade-in">
             <GlassCard className="w-full max-w-2xl p-10 border-t-8" style={{ borderTopColor: THEME_MAIN }}>
                <div className="text-center mb-8">
                  <div className="bg-red-50 w-16 h-16 rounded-2xl flex items-center justify-center mx-auto mb-4">
                    {activeTab === 'assignSub' ? <CreditCard size={32} style={{ color: THEME_MAIN }} /> : <Dumbbell size={32} style={{ color: THEME_MAIN }} />}
                  </div>
                  <h3 className="text-2xl font-bold text-gray-900">Assign {activeTab === 'assignSub' ? 'Subscription' : 'Session Pack'}</h3>
                  <p className="text-gray-500">Link a member to a new plan</p>
                </div>

                <div className="space-y-8">
                  <div>
                    <label className="block text-sm font-bold text-gray-700 mb-2 uppercase tracking-wide">Member</label>
                    <div className="flex gap-3">
                        <div className="w-1/3">
                          <PulseInput 
                              type="number" 
                              placeholder="ID" 
                              value={assignmentMemberId} 
                              onChange={e => setAssignmentMemberId(e.target.value)} 
                              className="text-center font-mono font-bold text-lg"
                          />
                        </div>
                        <select 
                            className="flex-1 border-b-2 border-gray-200 bg-gray-50 px-4 py-2 rounded-lg text-sm focus:border-red-500 outline-none cursor-pointer hover:bg-gray-100 transition" 
                            onChange={e => setAssignmentMemberId(e.target.value)}
                            value=""
                        >
                            <option value="">Search by Name...</option>
                            {members.map(m => <option key={m.id} value={m.id}>{m.name} | {m.phoneNo}</option>)}
                        </select>
                    </div>
                  </div>

                  <div>
                    <label className="block text-sm font-bold text-gray-700 mb-2 uppercase tracking-wide">Plan Bundle</label>
                    <div className="flex gap-3">
                        <div className="w-1/3">
                          <PulseInput 
                              type="number" 
                              placeholder="ID" 
                              value={assignmentItemId} 
                              onChange={e => setAssignmentItemId(e.target.value)} 
                              className="text-center font-mono font-bold text-lg"
                          />
                        </div>
                        <select 
                            className="flex-1 border-b-2 border-gray-200 bg-gray-50 px-4 py-2 rounded-lg text-sm focus:border-red-500 outline-none cursor-pointer hover:bg-gray-100 transition" 
                            onChange={e => setAssignmentItemId(e.target.value)}
                            value=""
                        >
                            <option value="">Select a Bundle...</option>
                            {activeTab === 'assignSub' 
                                ? subBundles.map(s => <option key={s.id} value={s.id}>{s.name} • {s.price} EGP • {s.durationInMonth} Mo</option>)
                                : sessionBundles.map(s => <option key={s.id} value={s.id}>{s.title} • {s.sessionsAmount} Sessions</option>)
                            }
                        </select>
                    </div>
                  </div>

                  <button 
                    onClick={() => handleAssign(activeTab === 'assignSub' ? 'sub' : 'session')} 
                    className="w-full text-white py-4 rounded-xl font-bold text-lg shadow-xl hover:shadow-2xl transition transform hover:-translate-y-1 mt-4" 
                    style={{ backgroundColor: THEME_MAIN }}
                  >
                      Confirm Assignment
                  </button>
                </div>
             </GlassCard>
           </div>
      )}

      {activeTab === 'activeSubs' && (
          <GlassCard className="animate-fade-in">
              <table className="w-full text-left">
                  <thead className="bg-gray-50 text-gray-500 text-xs uppercase font-semibold tracking-wider">
                      <tr>
                          <th className="p-5">Sub ID</th>
                          <th className="p-5">Member</th>
                          <th className="p-5">Bundle Plan</th>
                          <th className="p-5">Expires On</th>
                          <th className="p-5 text-right">Actions</th>
                      </tr>
                  </thead>
                  <tbody className="divide-y divide-gray-50">
                      {activeSubscriptions.map(sub => (
                          <tr key={sub.id} className="hover:bg-gray-50 transition group">
                              <td className="p-5 font-mono text-gray-400">#{sub.id}</td>
                              <td className="p-5">
                                <div className="font-bold text-gray-900">{sub.member?.name || 'Unknown'}</div>
                                <div className="text-xs text-gray-400">ID: {sub.memberId}</div>
                              </td>
                              <td className="p-5 text-gray-600">{sub.bundle?.name || `Bundle #${sub.subscriptionBundleId}`}</td>
                              <td className="p-5">
                                <span className="bg-red-50 text-red-800 px-2 py-1 rounded text-xs font-mono">{sub.expDate}</span>
                              </td>
                              <td className="p-5 text-right">
                                  <button onClick={() => handleTerminateSub(sub.id)} className="text-gray-400 hover:text-red-600 text-sm font-semibold flex items-center gap-1 ml-auto transition group-hover:visible invisible">
                                      <XCircle size={16} /> Terminate
                                  </button>
                              </td>
                          </tr>
                      ))}
                      {activeSubscriptions.length === 0 && <tr><td colSpan="5" className="p-12 text-center text-gray-400">No active subscriptions found.</td></tr>}
                  </tbody>
              </table>
          </GlassCard>
      )}

      {activeTab === 'checkin' && (
          <div className="space-y-8 max-w-4xl mx-auto animate-fade-in">
              {/* Check In Form */}
              <GlassCard className="p-10 text-center border-t-8" style={{ borderTopColor: THEME_MAIN }}>
                  <div className="bg-red-50 w-20 h-20 rounded-full flex items-center justify-center mx-auto mb-6">
                    <Calendar className="w-10 h-10" style={{ color: THEME_MAIN }} />
                  </div>
                  <h3 className="text-3xl font-extrabold mb-2 text-gray-900">Member Check-In</h3>
                  <p className="text-gray-500 mb-8">Enter ID to log attendance</p>
                  
                  <form onSubmit={handleCheckIn} className="max-w-xs mx-auto">
                      <PulseInput 
                          placeholder="Member ID" 
                          className="mb-6 text-center text-4xl font-mono font-bold tracking-widest h-20" 
                          value={checkInId} 
                          onChange={e => setCheckInId(e.target.value)} 
                          autoFocus
                      />
                      <button type="submit" className="w-full text-white py-4 rounded-xl font-bold hover:opacity-90 shadow-xl text-lg transition transform hover:-translate-y-1" style={{ backgroundColor: THEME_MAIN }}>
                          Check In Now
                      </button>
                  </form>
              </GlassCard>

              {/* Recent Attendances */}
              {recentAttendances.length > 0 && (
                <div className="bg-white/50 backdrop-blur rounded-2xl p-6">
                    <h4 className="font-bold text-gray-500 uppercase text-xs tracking-wider mb-4 pl-2">Recent Activity (Session)</h4>
                    <ul className="space-y-3">
                        {recentAttendances.map((rec, i) => (
                            <GlassCard key={i} className="flex justify-between items-center p-4 !border-l-4 !rounded-xl" style={{ borderLeftColor: rec.status.toLowerCase().includes('successful') ? '#10b981' : '#ef4444' }}>
                                <div className="flex items-center gap-3">
                                  <div className={`p-2 rounded-full ${rec.status.toLowerCase().includes('successful') ? 'bg-green-100' : 'bg-red-100'}`}>
                                    {rec.status.toLowerCase().includes('successful') ? <CheckCircle size={16} className="text-green-600" /> : <XCircle size={16} className="text-red-600" />}
                                  </div>
                                  <div>
                                    <span className="font-bold text-gray-900 block">{rec.memberName}</span> 
                                    <span className="text-xs text-gray-400">ID: {rec.memberId}</span>
                                  </div>
                                </div>
                                <div className="text-right">
                                    <span className={`font-bold block text-sm ${rec.status.toLowerCase().includes('successful') ? 'text-green-600' : 'text-red-500'}`}>{rec.status.replace(/Check-in successful for member: |Error: /g, '')}</span>
                                    <span className="text-xs text-gray-400 font-mono">{rec.time}</span>
                                </div>
                            </GlassCard>
                        ))}
                    </ul>
                </div>
              )}
          </div>
      )}

    </DashboardShell>
  );
};